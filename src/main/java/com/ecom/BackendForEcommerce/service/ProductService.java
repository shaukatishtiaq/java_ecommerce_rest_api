package com.ecom.BackendForEcommerce.service;

import com.ecom.BackendForEcommerce.dto.product.ProductUpdateDto;
import com.ecom.BackendForEcommerce.entity.Category;
import com.ecom.BackendForEcommerce.entity.Product;
import com.ecom.BackendForEcommerce.enums.ProductQuantityOperationEnum;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product, Long productCategoryId);

    List<Product> getAllProducts();

    Product getProductById(Long productId);

    Product updateProductQuantity(Long productId, Integer productQuantity, ProductQuantityOperationEnum productQuantityOperation);

    Product updateProductDetails(Long productId, ProductUpdateDto productPayload);

    Product deleteProductById(Long productId);

    Product createProductAndUploadImage(Product product, Long productCategoryId, MultipartFile imageFile);

    Product updateProductImage(Long productId, MultipartFile imageFile);

    void deleteProductsByCategory(Category category);
}
