package com.ecommerce.oms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.oms.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	/*Spring Data JPA will auto-generate:
		SELECT * FROM products WHERE active = true;
	No custom query needed (clean & readable)*/
	
	List<Product> findByActiveTrue();

		
    }
