package com.ecommerce.oms.dto;

import lombok.Data;

@Data
public class OrderItemResponseDTO {

    private Long productId;
    private String productName;
    private double price;
    private int quantity;
    private double subTotal;
}
