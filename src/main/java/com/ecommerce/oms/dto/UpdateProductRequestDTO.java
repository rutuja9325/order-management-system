package com.ecommerce.oms.dto;

import lombok.Data;

@Data
public class UpdateProductRequestDTO {
	private String name;
    private String description;
    private Double price;
    private String brand;
    private Integer inventoryCount;
    private String sku;
}
