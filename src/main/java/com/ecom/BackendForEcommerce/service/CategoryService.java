package com.ecom.BackendForEcommerce.service;

import com.ecom.BackendForEcommerce.dto.category.CategoryUpdateDto;
import com.ecom.BackendForEcommerce.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);

    Category getCategoryById(Long categoryId);

    List<Category> getAllCategories();

    Category updateCategoryById(Long categoryId, CategoryUpdateDto categoryUpdatePayload);

    Category deleteCategoryById(Long categoryId);
}
