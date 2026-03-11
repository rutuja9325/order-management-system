package com.ecommerce.oms.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.oms.dto.CustomerRequestDTO;
import com.ecommerce.oms.dto.CustomerResponseDTO;
import com.ecommerce.oms.dto.OrderResponseDTO;
import com.ecommerce.oms.entity.Customer;
import com.ecommerce.oms.entity.Order;
import com.ecommerce.oms.exception.ResourceNotFoundException;
import com.ecommerce.oms.repository.CustomerRepository;
import com.ecommerce.oms.repository.OrderRepository;



@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public CustomerService(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    public CustomerResponseDTO createCustomer(CustomerRequestDTO request) {

        Customer customer = new Customer();
        customer.setCompanyName(request.getCompanyName());
        customer.setContactPerson(request.getContactPerson());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());

        Customer savedCustomer = customerRepository.save(customer);
        return mapToDTO(savedCustomer);
    }

    public CustomerResponseDTO getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found with id: " + id)
                );

        return mapToDTO(customer);
    }

    // INTERNAL use later (OrderService)
    public Customer getCustomerEntityById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found with id: " + id)
                );
    }

    private CustomerResponseDTO mapToDTO(Customer customer) {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId(customer.getId());
        dto.setCompanyName(customer.getCompanyName());
        dto.setContactPerson(customer.getContactPerson());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());
        return dto;
    }
    
    private OrderResponseDTO mapToOrderDTO(Order order) {

        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus().name()); // ✅ FIX HERE
        dto.setTotalAmount(order.getTotalAmount());

        return dto;
    }

    
    
    public List<OrderResponseDTO> getOrdersForCustomer(Long customerId) {

        // 1️⃣ Validate customer exists
        getCustomerEntityById(customerId);

        // 2️⃣ Fetch orders
        List<Order> orders = orderRepository.findByCustomerId(customerId);

        // 3️⃣ Convert to DTO
        return orders.stream()
                .map(this::mapToOrderDTO)
                .collect(Collectors.toList());
    }
    
}
