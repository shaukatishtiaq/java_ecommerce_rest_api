package com.ecom.BackendForEcommerce.repository;

import com.ecom.BackendForEcommerce.entity.Category;
import com.ecom.BackendForEcommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    void deleteAllByCategory(Category category);

    Boolean existsByProductName(String productName);
}
