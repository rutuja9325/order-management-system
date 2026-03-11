package com.ecommerce.oms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.oms.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
