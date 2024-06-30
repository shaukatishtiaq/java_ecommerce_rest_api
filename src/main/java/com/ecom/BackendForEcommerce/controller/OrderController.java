package com.ecom.BackendForEcommerce.controller;

import com.ecom.BackendForEcommerce.dto.order.OrderCreateDto;
import com.ecom.BackendForEcommerce.dto.order.OrderResponseDto;
import com.ecom.BackendForEcommerce.dto.orderitem.OrderItemResponseDto;
import com.ecom.BackendForEcommerce.entity.Order;
import com.ecom.BackendForEcommerce.entity.OrderItem;
import com.ecom.BackendForEcommerce.enums.OrderStatusEnum;
import com.ecom.BackendForEcommerce.service.OrderService;
import com.ecom.BackendForEcommerce.service.ProductService;
import com.ecom.BackendForEcommerce.utility.SecurityContextUtility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ModelMapper modelMapper;
    private final ProductService productService;

    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDto> createOrderUsingUserId(@Valid @RequestBody OrderCreateDto orderPayload) {

        String userEmail = SecurityContextUtility.getUserEmailFromSecurityContext();

        Order savedOrder = orderService.createOrder(userEmail, orderPayload);

        List<OrderItem> savedOrderItem = savedOrder.getOrderItems();
        List<OrderItemResponseDto> orderItemResponse = savedOrderItem.stream().map(orderItem -> modelMapper.map(orderItem, OrderItemResponseDto.class)).toList();

        OrderResponseDto result = modelMapper.map(savedOrder, OrderResponseDto.class);

        result.setOrderItemList(orderItemResponse);


        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderByOrderId(@PathVariable("orderId") Long orderId) {

        String userEmail = SecurityContextUtility.getUserEmailFromSecurityContext();

        Order placedOrder = orderService.getOrderByOrderId(userEmail, orderId);

        List<OrderItem> orderItemList = placedOrder.getOrderItems();

        List<OrderItemResponseDto> orderItemsResponseList = orderItemList.stream().map(orderItem -> modelMapper.map(orderItem, OrderItemResponseDto.class)).toList();

        OrderResponseDto result = modelMapper.map(placedOrder, OrderResponseDto.class);
        result.setOrderItemList(orderItemsResponseList);

        System.out.println(result.toString());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> getAllOrdersByUser() {
        String userEmail = SecurityContextUtility.getUserEmailFromSecurityContext();

        List<Order> userOrders = orderService.getAllOrdersByUser(userEmail);

        List<OrderResponseDto> result = userOrders.stream().map(order -> {
            List<OrderItemResponseDto> orderItemsResponse = order.getOrderItems().stream().map(orderItem -> modelMapper.map(orderItem, OrderItemResponseDto.class)).toList();

            OrderResponseDto orderResponse = modelMapper.map(order, OrderResponseDto.class);
            orderResponse.setOrderItemList(orderItemsResponse);

            return orderResponse;

        }).toList();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/admin/orders")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {

        List<Order> orders = orderService.getAllOrders();

        List<OrderResponseDto> result = orders.stream().map(order -> {
            List<OrderItemResponseDto> orderItemsResponse = order.getOrderItems().stream().map(orderItem -> modelMapper.map(orderItem, OrderItemResponseDto.class)).toList();

            OrderResponseDto orderResponse = modelMapper.map(order, OrderResponseDto.class);
            orderResponse.setOrderItemList(orderItemsResponse);

            return orderResponse;
        }).toList();

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PatchMapping("/admin/orders/{orderId}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(@PathVariable("orderId") Long orderId, @RequestParam("ORDER_STATUS") OrderStatusEnum orderStatus) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, orderStatus);

        List<OrderItem> orderItemList = updatedOrder.getOrderItems();

        List<OrderItemResponseDto> orderItemsResponseList = orderItemList.stream().map(orderItem -> modelMapper.map(orderItem, OrderItemResponseDto.class)).toList();

        OrderResponseDto result = modelMapper.map(updatedOrder, OrderResponseDto.class);
        result.setOrderItemList(orderItemsResponseList);


        System.out.println(result.toString());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
