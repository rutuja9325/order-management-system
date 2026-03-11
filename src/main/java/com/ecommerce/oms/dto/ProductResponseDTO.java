package com.ecommerce.oms.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {

	private Long id;
    private String itemNumber;
    private String name;
    private String brand;
    private Double price;
    private Integer inventoryCount;
    private Boolean active;

    // getters & setters
    
}
