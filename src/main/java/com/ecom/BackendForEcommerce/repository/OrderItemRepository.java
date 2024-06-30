package com.ecom.BackendForEcommerce.repository;

import com.ecom.BackendForEcommerce.entity.Order;
import com.ecom.BackendForEcommerce.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    void deleteAllByOrder(Order order);
}
