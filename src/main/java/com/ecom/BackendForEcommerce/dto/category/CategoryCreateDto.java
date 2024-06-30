package com.ecom.BackendForEcommerce.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CategoryCreateDto {
    @NotBlank(message = "Category name can't be empty")
    private String categoryName;
}
