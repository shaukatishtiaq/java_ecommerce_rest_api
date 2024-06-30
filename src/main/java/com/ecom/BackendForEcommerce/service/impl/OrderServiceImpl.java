package com.ecom.BackendForEcommerce.service.impl;

import com.ecom.BackendForEcommerce.dto.order.OrderCreateDto;
import com.ecom.BackendForEcommerce.entity.*;
import com.ecom.BackendForEcommerce.enums.OrderStatusEnum;
import com.ecom.BackendForEcommerce.enums.ProductQuantityOperationEnum;
import com.ecom.BackendForEcommerce.exception.customexception.CommonCustomException;
import com.ecom.BackendForEcommerce.repository.OrderItemRepository;
import com.ecom.BackendForEcommerce.repository.OrderRepository;
import com.ecom.BackendForEcommerce.service.AddressService;
import com.ecom.BackendForEcommerce.service.OrderService;
import com.ecom.BackendForEcommerce.service.ProductService;
import com.ecom.BackendForEcommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;
    private final AddressService addressService;
    private final ProductService productService;

    @Override
    @Transactional
    public Order createOrder(String userEmail, OrderCreateDto orderPayload) {
        try {
            User user = userService.findByEmail(userEmail);

            Address address = addressService.getAddressById(userEmail, orderPayload.getAddressId());

            Product product = productService.getProductById(orderPayload.getProductId());

            if (product.getProductQuantity() < orderPayload.getProductQuantity()) {
                throw new CommonCustomException(HttpStatus.BAD_REQUEST.value(), "Order quantity > product in stock.");
            }

            Order order = Order.builder()
                    .user(user)
                    .orderAddress(address)
                    .mobileNumber(orderPayload.getMobileNumber())
                    .orderStatusEnum(OrderStatusEnum.PLACED)
                    .build();

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .productQuantity(orderPayload.getProductQuantity())
                    .build();

            orderItem.calculateAndSetProductsPrice();
            if (order.getOrderItems() == null) {
                order.setOrderItems(new ArrayList<>());
            }
            order.getOrderItems().addLast(orderItem);
            order.calculateAndSetTotalOrderPrice();

            productService.updateProductQuantity(product.getProductId(), orderItem.getProductQuantity(), ProductQuantityOperationEnum.SUBTRACT);

            return orderRepository.save(order);
        } catch (CommonCustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonCustomException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error while creating the order.");
        }
    }

    @Override
    public Order getOrderByOrderId(String userEmail, Long orderId) {
        User user = userService.findByEmail(userEmail);

        Optional<Order> foundOrderOptional = orderRepository.findById(orderId);

        if (foundOrderOptional.isEmpty()) {
            throw new CommonCustomException(HttpStatus.NOT_FOUND.value(), "Order with order id = " + orderId + " not found.");
        }

        Order foundOrder = foundOrderOptional.get();

        if (foundOrder.getUser().equals(user)) {
            return foundOrderOptional.get();
        } else {
            throw new CommonCustomException(HttpStatus.UNAUTHORIZED.value(), "You are not authorized to access this resource.");
        }

    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatusEnum orderStatus) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);

        if (orderOptional.isEmpty()) {
            throw new CommonCustomException(HttpStatus.NOT_FOUND.value(), "Order with order id = " + orderId + " not found.");
        }

        Order order = orderOptional.get();

        order.setOrderStatusEnum(orderStatus);

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrdersByUser(String userEmail) {
        User user = userService.findByEmail(userEmail);

        return orderRepository.findByUser(user);

    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrdersByUser(User user) {
        List<Order> orders = orderRepository.findByUser(user);

        orders.forEach(order -> {
            orderItemRepository.deleteAllByOrder(order);
            orderRepository.delete(order);
        });
    }

}
