package com.ecom.BackendForEcommerce.dto.orderitem;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OrderItemCreateDto {
    @NotNull
    private Long productId;

    @NotNull
    private Integer productQuantity;
}
