package com.ecommerce.oms.dto;

import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateOrderRequestDTO {

    @NotEmpty
    private List<CreateOrderItemRequestDTO> items;
}
