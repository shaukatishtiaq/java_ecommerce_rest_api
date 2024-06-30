package com.ecom.BackendForEcommerce.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserResponseDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
}
