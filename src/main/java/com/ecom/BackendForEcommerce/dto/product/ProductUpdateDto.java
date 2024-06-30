package com.ecom.BackendForEcommerce.dto.product;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductUpdateDto {

    private Long categoryId;

    private String productName;

    private Double productPrice;

    private Integer productQuantity;
}
