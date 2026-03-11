package com.ecommerce.oms.specification;

import com.ecommerce.oms.entity.Order;
import com.ecommerce.oms.entity.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderSpecification {

    public static Specification<Order> filterOrders(
            OrderStatus status,
            Long customerId,
            LocalDate from,
            LocalDate to) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (status != null) {
                predicates.add(
                    cb.equal(root.get("status"), status)
                );
            }

            if (customerId != null) {
                predicates.add(
                    cb.equal(root.get("customer").get("id"), customerId)
                );
            }

            if (from != null) {
                predicates.add(
                    cb.greaterThanOrEqualTo(
                        root.get("orderDate"),
                        from.atStartOfDay()
                    )
                );
            }

            if (to != null) {
                predicates.add(
                    cb.lessThanOrEqualTo(
                        root.get("orderDate"),
                        to.atTime(23,59,59)
                    )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}