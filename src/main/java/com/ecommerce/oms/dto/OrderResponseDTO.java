package com.ecommerce.oms.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrderResponseDTO {

    private Long id;
    private String orderNumber;
    private Double totalAmount;
    private String status;
    private LocalDateTime orderDate;
}
