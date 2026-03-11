package com.ecommerce.oms.service;


import com.ecommerce.oms.dto.CreateOrderItemRequestDTO;
import com.ecommerce.oms.dto.CreateOrderRequestDTO;
import com.ecommerce.oms.dto.OrderItemResponseDTO;
import com.ecommerce.oms.dto.OrderResponseDTO;
import com.ecommerce.oms.dto.OrderDetailsResponseDTO;
import com.ecommerce.oms.dto.CustomerSummaryDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.oms.entity.Customer;
import com.ecommerce.oms.entity.Order;
import com.ecommerce.oms.entity.OrderItem;
import com.ecommerce.oms.entity.OrderStatus;
import com.ecommerce.oms.entity.Product;

import com.ecommerce.oms.repository.OrderRepository;
import com.ecommerce.oms.specification.OrderSpecification;
import com.ecommerce.oms.exception.OutOfStockException;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CustomerService customerService;


    public OrderService(OrderRepository orderRepository,
            ProductService productService,
            CustomerService customerService) {

        this.orderRepository = orderRepository;
        this.productService = productService;
        this.customerService = customerService;
    }

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
    
    public Order saveOrder(Order order) {
        
        // ✅ Auto status
        order.setStatus(OrderStatus.PENDING);
        
        return orderRepository.save(order);
    }
    
    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return mapToDTO(order);
    }
    
    @Transactional
    public OrderResponseDTO createOrder(CreateOrderRequestDTO request) {

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setOrderNumber("ORD-" + System.currentTimeMillis());
        order.setStatus(OrderStatus.PENDING);

        double totalAmount = 0;

        List<OrderItem> orderItems = new ArrayList<>();

        for (CreateOrderItemRequestDTO itemDTO : request.getItems()) {

        	Product product = productService.getActiveProductEntityById(itemDTO.getProductId());

            
        	if (product.getInventoryCount() < itemDTO.getQuantity()) {
        	    throw new OutOfStockException(
        	        "Product '" + product.getName() +
        	        "' has only " + product.getInventoryCount() +
        	        " items left"
        	    );
        	}

            // 🔽 Reduce inventory
            product.setInventoryCount(
                product.getInventoryCount() - itemDTO.getQuantity()
                
            );  
            

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(product.getPrice());

            totalAmount += product.getPrice() * itemDTO.getQuantity();
            orderItems.add(item);
        }

        order.setTotalAmount(totalAmount);
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        return mapToDTO(savedOrder);
    }
    
    
    private OrderResponseDTO mapToDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus().name());
        dto.setOrderDate(order.getOrderDate());
        return dto;
    }
    
    /*Uses transaction
	Enforces state machine
	Prevents illegal transitions*/
    
    @Transactional
    public OrderResponseDTO approveOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // ✅ Rule: only PENDING → APPROVED
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException(
                    "Only PENDING orders can be approved. Current status: " + order.getStatus()
            );
        }

        order.setStatus(OrderStatus.APPROVED);

        Order savedOrder = orderRepository.save(order);
        return mapToDTO(savedOrder);
    }
    
    private Order getOrderEntityById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found with id: " + id)
                );
    }
    
    @Transactional
    public OrderResponseDTO shipOrder(Long orderId) {

        Order order = getOrderEntityById(orderId); // ✅ ENTITY

        if (order.getStatus() != OrderStatus.APPROVED) {
            throw new IllegalStateException(
                    "Only APPROVED orders can be shipped. Current status: " + order.getStatus()
            );
        }

        order.setStatus(OrderStatus.SHIPPED);

        Order savedOrder = orderRepository.save(order);

        return mapToDTO(savedOrder);  // correct mapper
    }
    
    
    /*When an order is cancelled:

For each OrderItem:

product.inventory += item.quantity


 This must happen in the same transaction
If anything fails → rollback everything

 Why this matters

This step tests:

Transaction management

Entity relationships

Business rule enforcement

Data consistency */
    
    @Transactional
    public OrderResponseDTO cancelOrder(Long orderId) {

        Order order = getOrderEntityById(orderId);

        // Cannot cancel shipped or delivered
        if (order.getStatus() != OrderStatus.PENDING &&
        	    order.getStatus() != OrderStatus.APPROVED) {

        	    throw new IllegalStateException(
        	        "Only PENDING or APPROVED orders can be cancelled. Current status: "
        	        + order.getStatus()
        	    );
        	}


        // Restore inventory
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setInventoryCount(
                product.getInventoryCount() + item.getQuantity()
            );
        }

        order.setStatus(OrderStatus.CANCELLED);

        Order savedOrder = orderRepository.save(order);

        return mapToDTO(savedOrder);
    }


    @Transactional
    public OrderResponseDTO createOrderForCustomer(
            Long customerId,
            CreateOrderRequestDTO request) {

        Customer customer =
                customerService.getCustomerEntityById(customerId);

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setOrderNumber("ORD-" + System.currentTimeMillis());
        order.setStatus(OrderStatus.PENDING);
        order.setCustomer(customer);

        double totalAmount = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CreateOrderItemRequestDTO itemDTO : request.getItems()) {

            Product product =
                    productService.getActiveProductEntityById(itemDTO.getProductId());

            if (product.getInventoryCount() < itemDTO.getQuantity()) {
                throw new OutOfStockException(
                    "Product '" + product.getName() +
                    "' has only " + product.getInventoryCount() + " items left"
                );
            }

            product.setInventoryCount(
                    product.getInventoryCount() - itemDTO.getQuantity()
            );

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(product.getPrice());

            totalAmount += product.getPrice() * itemDTO.getQuantity();
            orderItems.add(item);
        }

        order.setTotalAmount(totalAmount);
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        return mapToDTO(savedOrder);
    }
    
    @Transactional(readOnly = true)
    public OrderDetailsResponseDTO getOrderDetails(Long orderId) {

    	Order order = orderRepository.findOrderWithDetails(orderId);

    	if (order == null) {
    	    throw new RuntimeException("Order not found with id: " + orderId);
    	}

        OrderDetailsResponseDTO dto = new OrderDetailsResponseDTO();

        dto.setOrderId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setStatus(order.getStatus().name());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setOrderDate(order.getOrderDate());

        // 👤 Customer summary
        Customer customer = order.getCustomer();
        CustomerSummaryDTO customerDTO = new CustomerSummaryDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setCompanyName(customer.getCompanyName());
        customerDTO.setContactPerson(customer.getContactPerson());
        customerDTO.setEmail(customer.getEmail());

        dto.setCustomer(customerDTO);

        // 📦 Order items
        List<OrderItemResponseDTO> items = order.getItems()
                .stream()
                .map(item -> {

                    OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
                    itemDTO.setProductId(item.getProduct().getId());
                    itemDTO.setProductName(item.getProduct().getName());
                    itemDTO.setPrice(item.getPrice());
                    itemDTO.setQuantity(item.getQuantity());
                    itemDTO.setSubTotal(item.getPrice() * item.getQuantity());

                    return itemDTO;
                })
                .toList();

        dto.setItems(items);

        return dto;
    }

    public Page<OrderResponseDTO> getOrders(int page, int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("orderDate").descending()
        );

        return orderRepository.findAll(pageable)
                .map(this::mapToDTO);
    }
    
    @Transactional(readOnly = true)
    public Page<OrderResponseDTO> searchOrders(
            OrderStatus status,
            Long customerId,
            LocalDate from,
            LocalDate to,
            int page,
            int size) {

        var pageable = PageRequest.of(
                page,
                size,
                Sort.by("orderDate").descending()
        );

        var specification = 
                OrderSpecification.filterOrders(status, customerId, from, to);

        return orderRepository
                .findAll(specification, pageable)
                .map(this::mapToDTO);
    }


}
