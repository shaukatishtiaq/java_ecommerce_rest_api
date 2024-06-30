package com.ecom.BackendForEcommerce.controller;

import com.ecom.BackendForEcommerce.dto.category.CategoryCreateDto;
import com.ecom.BackendForEcommerce.dto.category.CategoryResponseDto;
import com.ecom.BackendForEcommerce.dto.category.CategoryUpdateDto;
import com.ecom.BackendForEcommerce.entity.Category;
import com.ecom.BackendForEcommerce.service.CategoryService;
import com.ecom.BackendForEcommerce.service.DeletionCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final DeletionCategoryService deletionCategoryService;
    private final ModelMapper modelMapper;

    @PostMapping("/admin/category")
    public ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CategoryCreateDto categoryPayload) {

        Category category = modelMapper.map(categoryPayload, Category.class);

        Category savedCategory = categoryService.createCategory(category);

        CategoryResponseDto result = modelMapper.map(savedCategory, CategoryResponseDto.class);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable("categoryId") Long categoryId) {
        Category foundCategory = categoryService.getCategoryById(categoryId);

        CategoryResponseDto result = modelMapper.map(foundCategory, CategoryResponseDto.class);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        List<Category> foundCategories = categoryService.getAllCategories();

        List<CategoryResponseDto> result = foundCategories.stream().map((category) -> modelMapper.map(category, CategoryResponseDto.class)).toList();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/admin/category/{categoryId}")
    public ResponseEntity<CategoryResponseDto> updateCategoryById(@PathVariable("categoryId") Long categoryId, @Valid @RequestBody CategoryUpdateDto categoryUpdatePayload) {
        Category updatedCategory = categoryService.updateCategoryById(categoryId, categoryUpdatePayload);

        CategoryResponseDto result = modelMapper.map(updatedCategory, CategoryResponseDto.class);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/admin/category/{categoryId}")
    public ResponseEntity<CategoryResponseDto> updateCategoryById(@PathVariable("categoryId") Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);

        deletionCategoryService.deleteProductsByCategory(category);

        Category deletedCategory = categoryService.deleteCategoryById(categoryId);

        CategoryResponseDto result = modelMapper.map(deletedCategory, CategoryResponseDto.class);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
