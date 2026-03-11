package com.ecommerce.oms.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


/*
 An Order belongs to one Customer

A Customer can have many Orders

APIs to:

Create customer

Place order for a customer

Fetch all orders of a customer

 */


@Entity
@Table(name = "customers")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;

    private String contactPerson;

    private String email;

    private String phone;

    private String address;

    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    // getters & setters
}
