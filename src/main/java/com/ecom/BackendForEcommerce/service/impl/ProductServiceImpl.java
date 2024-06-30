package com.ecom.BackendForEcommerce.service.impl;

import com.ecom.BackendForEcommerce.dto.product.ProductUpdateDto;
import com.ecom.BackendForEcommerce.entity.Category;
import com.ecom.BackendForEcommerce.entity.Product;
import com.ecom.BackendForEcommerce.enums.ProductQuantityOperationEnum;
import com.ecom.BackendForEcommerce.exception.customexception.CommonCustomException;
import com.ecom.BackendForEcommerce.repository.ProductRepository;
import com.ecom.BackendForEcommerce.service.CategoryService;
import com.ecom.BackendForEcommerce.service.ImageStorageService;
import com.ecom.BackendForEcommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ImageStorageService imageStorageService;

    @Override
    public Product createProductAndUploadImage(Product product, Long productCategoryId, MultipartFile imageFile) {
        if (productRepository.existsByProductName(product.getProductName())) {
            throw new CommonCustomException(HttpStatus.BAD_REQUEST.value(), "Product with this name already exists.");
        }
        String imageUrl = imageStorageService.uploadImage(imageFile);

        Category category = categoryService.getCategoryById(productCategoryId);

        product.setProductId(null);
        product.setCategory(category);
        product.setProductImageUrl(imageUrl);

        return productRepository.save(product);

    }

    @Override
    public Product updateProductImage(Long productId, MultipartFile imageFile) {
        Product product = getProductById(productId);

        String imageUrl = imageStorageService.uploadImage(imageFile);

        product.setProductImageUrl(imageUrl);

        return productRepository.save(product);
    }

    @Override
    public void deleteProductsByCategory(Category category) {
        productRepository.deleteAllByCategory(category);
    }

    //Not used anymore ------------ START
    @Override
    public Product createProduct(Product product, Long productCategoryId) {
        Category productCategory = categoryService.getCategoryById(productCategoryId);

        product.setCategory(productCategory);

        product.setProductId(null);

        return productRepository.save(product);
    }
    //Not used anymore ------------ END


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long productId) {
        Optional<Product> savedProductOptional = productRepository.findById(productId);

        if (savedProductOptional.isEmpty()) {
            throw new CommonCustomException(HttpStatus.NOT_FOUND.value(), "Product with id = " + productId + " not found.");
        }
        return savedProductOptional.get();
    }

    @Override
    public Product updateProductQuantity(Long productId, Integer productQuantity, ProductQuantityOperationEnum productQuantityOperation) {
        Optional<Product> savedProductOptional = productRepository.findById(productId);


        if (savedProductOptional.isEmpty()) {
            throw new CommonCustomException(HttpStatus.NOT_FOUND.value(), "Product with id = " + productId + " not found.");
        }

        Product product = savedProductOptional.get();

        if (productQuantityOperation == ProductQuantityOperationEnum.ADD) {
            product.setProductQuantity(product.getProductQuantity() + productQuantity);
        } else if (productQuantityOperation == ProductQuantityOperationEnum.SUBTRACT) {
            product.setProductQuantity(product.getProductQuantity() - productQuantity);
        } else if (productQuantityOperation == ProductQuantityOperationEnum.SET) {
            product.setProductQuantity(productQuantity);
        } else if (productQuantityOperation == ProductQuantityOperationEnum.REMOVE) {
            product.setProductQuantity(0);
        } else {
            throw new CommonCustomException(HttpStatus.BAD_REQUEST.value(), "Operation with value " + productQuantityOperation.name() + " doens't match any value in the enum");
        }

        return productRepository.save(product);
    }

    @Override
    public Product updateProductDetails(Long productId, ProductUpdateDto productPayload) {
        Optional<Product> savedProductOptional = productRepository.findById(productId);

        if (savedProductOptional.isEmpty()) {
            throw new CommonCustomException(HttpStatus.NOT_FOUND.value(), "Product with id = " + productId + " not found.");
        }

        Product savedProduct = savedProductOptional.get();
        System.out.println("\n\nBefore\n" + savedProduct.toString());
        if (productPayload.getProductPrice() != null) {
            savedProduct.setProductPrice(productPayload.getProductPrice());
        }
        if (productPayload.getProductQuantity() != null) {
            savedProduct.setProductQuantity(productPayload.getProductQuantity());
        }
        if (productPayload.getProductName() != null) {
            savedProduct.setProductName(productPayload.getProductName());
        }
        if (productPayload.getCategoryId() != null) {
            Category productCategory = categoryService.getCategoryById(productPayload.getCategoryId());
            savedProduct.setCategory(productCategory);
        }
        System.out.println("\n\nAfter\n" + savedProduct.toString());
        return productRepository.save(savedProduct);
    }

    @Override
    public Product deleteProductById(Long productId) {
        Optional<Product> productToBeDeletedOptional = productRepository.findById(productId);

        if (productToBeDeletedOptional.isEmpty()) {
            throw new CommonCustomException(HttpStatus.NOT_FOUND.value(), "Product with id = " + productId + " not found.");
        }

        productRepository.deleteById(productId);

        return productToBeDeletedOptional.get();
    }


}
