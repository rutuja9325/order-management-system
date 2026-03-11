package com.ecommerce.oms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ecommerce.oms.dto.CreateOrderRequestDTO;
import com.ecommerce.oms.dto.CustomerRequestDTO;
import com.ecommerce.oms.dto.CustomerResponseDTO;
import com.ecommerce.oms.dto.OrderResponseDTO;
import com.ecommerce.oms.service.CustomerService;
import com.ecommerce.oms.service.OrderService;



@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final OrderService orderService;

    public CustomerController(CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }
    
    @PostMapping("/{customerId}/orders")
    public OrderResponseDTO createOrderForCustomer(
            @PathVariable Long customerId,
            @RequestBody CreateOrderRequestDTO request) {

        return orderService.createOrderForCustomer(customerId, request);
    }

    @PostMapping
    public CustomerResponseDTO createCustomer(
            @RequestBody CustomerRequestDTO request) {
        return customerService.createCustomer(request);
    }

    @GetMapping("/{id}")
    public CustomerResponseDTO getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }
    
    @GetMapping("/{customerId}/orders")
    public List<OrderResponseDTO> getOrdersForCustomer(
            @PathVariable Long customerId) {

        return customerService.getOrdersForCustomer(customerId);
    }

}
