package com.ecom.BackendForEcommerce.dto.orderitem;

import com.ecom.BackendForEcommerce.dto.product.ProductResponseDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OrderItemResponseDto {
    private Long orderItemId;

    private ProductResponseDto product;

    private Integer productQuantity;

    private Double totalProductsPrice;
}
