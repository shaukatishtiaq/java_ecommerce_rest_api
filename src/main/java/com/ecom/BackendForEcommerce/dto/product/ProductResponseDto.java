package com.ecom.BackendForEcommerce.dto.product;

import com.ecom.BackendForEcommerce.dto.category.CategoryResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductResponseDto {
    private Long productId;

    private CategoryResponseDto category;

    private String productName;

    private Double productPrice;

    private Integer productQuantity;

    @JsonProperty("image")
    private String productImageUrl;
}
