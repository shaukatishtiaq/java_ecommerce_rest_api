package com.ecom.BackendForEcommerce.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductCreateDto {
    @NotNull
    private Long categoryId;

    @NotBlank
    private String productName;

    @NotNull
    private Double productPrice = 1.0;

    @NotNull
    private Integer productQuantity = 1;

}
