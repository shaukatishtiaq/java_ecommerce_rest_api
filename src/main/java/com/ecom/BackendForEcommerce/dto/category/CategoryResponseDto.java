package com.ecom.BackendForEcommerce.dto.category;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CategoryResponseDto {
    private Long categoryId;

    private String categoryName;
}
