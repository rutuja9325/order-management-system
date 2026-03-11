package com.ecommerce.oms.dto;

import lombok.Data;

@Data
public class CustomerResponseDTO {

	
	private Long id;
    private String companyName;
    private String contactPerson;
    private String email;
    private String phone;
    private String address;
}
