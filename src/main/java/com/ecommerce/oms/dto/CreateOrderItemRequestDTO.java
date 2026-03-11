package com.ecommerce.oms.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateOrderItemRequestDTO {

    @NotNull
    private Long productId;

    @Positive
    private Integer quantity;
}
