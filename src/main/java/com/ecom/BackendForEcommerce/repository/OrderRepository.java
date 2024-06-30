package com.ecom.BackendForEcommerce.repository;

import com.ecom.BackendForEcommerce.entity.Order;
import com.ecom.BackendForEcommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
