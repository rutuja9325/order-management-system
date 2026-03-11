package com.ecommerce.oms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.oms.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>,  JpaSpecificationExecutor<Order>{
	
	// Get orders for a specific customer
	List<Order> findByCustomerId(Long customerId);
	
	
	// Get orders for a specific customer
	@Query("""
		    SELECT o FROM Order o
		    JOIN FETCH o.items i
		    JOIN FETCH i.product
		    WHERE o.id = :orderId
		""")
		Order findOrderWithDetails(@Param("orderId") Long orderId);

    
}
