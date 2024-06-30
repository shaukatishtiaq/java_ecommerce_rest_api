package com.ecom.BackendForEcommerce.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OrderCreateDto {

    private Long productId;
    private Long addressId;
    private Integer productQuantity;

    @NotBlank
    @Size(min = 10, max = 10)
    private String mobileNumber;
}
