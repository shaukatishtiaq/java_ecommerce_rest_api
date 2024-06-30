package com.ecom.BackendForEcommerce.dto.order;

import com.ecom.BackendForEcommerce.dto.orderitem.OrderItemResponseDto;
import com.ecom.BackendForEcommerce.entity.Address;
import com.ecom.BackendForEcommerce.enums.OrderStatusEnum;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OrderResponseDto {
    private Long orderId;

    private List<OrderItemResponseDto> orderItemList = new ArrayList<>();

    private Address orderAddress;

    private Double totalOrderPrice;

    private String mobileNumber;

    private OrderStatusEnum orderStatusEnum;
}
