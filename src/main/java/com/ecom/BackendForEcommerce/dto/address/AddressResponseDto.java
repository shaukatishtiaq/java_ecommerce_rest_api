package com.ecom.BackendForEcommerce.dto.address;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AddressResponseDto {
    private Long addressId;
    private String area;
    private String city;
    private String state;
}
