package com.ecommerce.oms.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.oms.entity.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
