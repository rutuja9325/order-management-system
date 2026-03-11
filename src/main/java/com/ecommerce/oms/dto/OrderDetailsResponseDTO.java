package com.ecommerce.oms.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;


@Data
public class OrderDetailsResponseDTO {

    private Long orderId;
    private String orderNumber;
    private String status;
    private double totalAmount;
    private LocalDateTime orderDate;

    private CustomerSummaryDTO customer;
    private List<OrderItemResponseDTO> items;
}
