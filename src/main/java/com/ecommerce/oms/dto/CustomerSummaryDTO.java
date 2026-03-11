package com.ecommerce.oms.dto;

import lombok.Data;

@Data
public class CustomerSummaryDTO {

    private Long id;
    private String companyName;
    private String contactPerson;
    private String email;
}
