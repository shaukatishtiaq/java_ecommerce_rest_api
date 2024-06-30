package com.ecom.BackendForEcommerce.service;

import com.ecom.BackendForEcommerce.entity.Category;

public interface DeletionCategoryService {
    void deleteProductsByCategory(Category category);
}
