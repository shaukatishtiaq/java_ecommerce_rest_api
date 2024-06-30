package com.ecom.BackendForEcommerce.service.impl;

import com.ecom.BackendForEcommerce.entity.Category;
import com.ecom.BackendForEcommerce.service.DeletionCategoryService;
import com.ecom.BackendForEcommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeletionCategoryServiceImpl implements DeletionCategoryService {
    private final ProductService productService;

    @Override
    @Transactional
    public void deleteProductsByCategory(Category category) {
        productService.deleteProductsByCategory(category);
    }
}
