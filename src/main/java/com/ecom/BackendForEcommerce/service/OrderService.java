package com.ecom.BackendForEcommerce.service;

import com.ecom.BackendForEcommerce.dto.order.OrderCreateDto;
import com.ecom.BackendForEcommerce.entity.Order;
import com.ecom.BackendForEcommerce.entity.User;
import com.ecom.BackendForEcommerce.enums.OrderStatusEnum;

import java.util.List;

public interface OrderService {
    Order createOrder(String userEmail, OrderCreateDto orderPayload);

    Order getOrderByOrderId(String userEmail, Long orderId);

    Order updateOrderStatus(Long orderId, OrderStatusEnum orderStatus);

    List<Order> getAllOrdersByUser(String userEmail);

    List<Order> getAllOrders();

    void deleteOrdersByUser(User user);
}
