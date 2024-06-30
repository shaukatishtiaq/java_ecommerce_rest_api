package com.ecom.BackendForEcommerce.service.impl;

import com.ecom.BackendForEcommerce.dto.category.CategoryUpdateDto;
import com.ecom.BackendForEcommerce.entity.Category;
import com.ecom.BackendForEcommerce.exception.customexception.CommonCustomException;
import com.ecom.BackendForEcommerce.repository.CategoryRepository;
import com.ecom.BackendForEcommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        if (categoryRepository.existsByCategoryName(category.getCategoryName())) {
            throw new CommonCustomException(HttpStatus.BAD_REQUEST.value(), "Category with name already exists.");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        Optional<Category> foundCategoryOptional = categoryRepository.findById(categoryId);

        if (foundCategoryOptional.isEmpty()) {
            throw new CommonCustomException(HttpStatus.NOT_FOUND.value(), "Category with category id = " + categoryId + " not found.");
        }
        return foundCategoryOptional.get();
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional
    public Category updateCategoryById(Long categoryId, CategoryUpdateDto categoryUpdatePayload) {
        Optional<Category> savedCategoryOptional = categoryRepository.findById(categoryId);
        if (savedCategoryOptional.isEmpty()) {
            throw new CommonCustomException(HttpStatus.NOT_FOUND.value(), "Category with category id = " + categoryId + " not found.");
        }

        Category savedCategory = savedCategoryOptional.get();

        savedCategory.setCategoryName(categoryUpdatePayload.getCategoryName());

        return categoryRepository.save(savedCategory);
    }

    @Override
    @Transactional
    public Category deleteCategoryById(Long categoryId) {
        Optional<Category> savedCategoryOptional = categoryRepository.findById(categoryId);
        if (savedCategoryOptional.isEmpty()) {
            throw new CommonCustomException(HttpStatus.NOT_FOUND.value(), "Category with category id = " + categoryId + " not found.");
        }

        categoryRepository.deleteById(categoryId);

        return savedCategoryOptional.get();
    }
}
