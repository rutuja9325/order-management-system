package com.ecommerce.oms.controller;

import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.oms.dto.CreateOrderRequestDTO;
import com.ecommerce.oms.dto.OrderDetailsResponseDTO;
import com.ecommerce.oms.dto.OrderResponseDTO;
import com.ecommerce.oms.entity.OrderStatus;
import com.ecommerce.oms.service.OrderService;

import jakarta.validation.Valid;




@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

        
    @GetMapping
    public Page<OrderResponseDTO> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return orderService.getOrders(page, size);
    }

    
    @PostMapping
    public OrderResponseDTO createOrder(
            @Valid @RequestBody CreateOrderRequestDTO request) {
        return orderService.createOrder(request);
    }
    
    @GetMapping("/{id}")
    public OrderResponseDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
    
    
    //Approved order
    @PatchMapping("/{id}/approve")
    public OrderResponseDTO approveOrder(@PathVariable Long id) {
        return orderService.approveOrder(id);
    }

    //Order Shipment
    @PatchMapping("/{id}/ship")
    public ResponseEntity<OrderResponseDTO> shipOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.shipOrder(id));
    }
    
    
    //Cancel Order
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }
    
    @GetMapping("/{orderId}/details")
    public OrderDetailsResponseDTO getOrderDetails(
            @PathVariable Long orderId) {

        return orderService.getOrderDetails(orderId);
    }

    
    @GetMapping("/search")
    public org.springframework.data.domain.Page<OrderResponseDTO> searchOrders(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return orderService.searchOrders(
                status, customerId, from, to, page, size);
    }


}



