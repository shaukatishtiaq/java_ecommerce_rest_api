package com.ecom.BackendForEcommerce.controller;

import com.ecom.BackendForEcommerce.dto.product.ProductCreateDto;
import com.ecom.BackendForEcommerce.dto.product.ProductResponseDto;
import com.ecom.BackendForEcommerce.dto.product.ProductUpdateDto;
import com.ecom.BackendForEcommerce.entity.Product;
import com.ecom.BackendForEcommerce.enums.ProductQuantityOperationEnum;
import com.ecom.BackendForEcommerce.exception.customexception.CommonCustomException;
import com.ecom.BackendForEcommerce.service.ProductService;
import com.ecom.BackendForEcommerce.utility.ImageValidationUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final Validator validator;

    @PostMapping(path = "/admin/products", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDto> createProduct(@RequestParam(value = "image", required = true) MultipartFile imageFile, @RequestParam("product") String productJsonString) {
        ImageValidationUtility.validateImage(imageFile);

        ProductCreateDto productPayload;

        try {
            productPayload = new ObjectMapper().readValue(productJsonString, ProductCreateDto.class);
        } catch (JsonProcessingException e) {
            throw new CommonCustomException(HttpStatus.BAD_REQUEST.value(), e.getOriginalMessage());
        }

        Product product = modelMapper.map(productPayload, Product.class);

        //            validating productPayload
        Set<ConstraintViolation<ProductCreateDto>> violations = validator.validate(productPayload);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Product savedProduct = productService.createProductAndUploadImage(product, productPayload.getCategoryId(), imageFile);

        ProductResponseDto result = modelMapper.map(savedProduct, ProductResponseDto.class);

        return new ResponseEntity<ProductResponseDto>(result, HttpStatus.CREATED);

    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<Product> products = productService.getAllProducts();

        List<ProductResponseDto> result = products.stream().map((product) -> modelMapper.map(product, ProductResponseDto.class)).toList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("productId") Long productId) {
        Product product = productService.getProductById(productId);

        ProductResponseDto result = modelMapper.map(product, ProductResponseDto.class);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/admin/products/{productId}/quantity")
    public ResponseEntity<ProductResponseDto> updateProductQuantity(@PathVariable("productId") Long productId,
                                                                    @RequestParam("product_quantity") Integer productQuantity,
                                                                    @RequestParam("operation") ProductQuantityOperationEnum productQuantityOperation) {
        Product updateProduct = productService.updateProductQuantity(productId, productQuantity, productQuantityOperation);

        ProductResponseDto result = modelMapper.map(updateProduct, ProductResponseDto.class);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PatchMapping("/admin/products/{productId}")
    public ResponseEntity<ProductResponseDto> updateProductDetails(@PathVariable("productId") Long productId, @RequestBody ProductUpdateDto productUpdatePayload) {

        Product updatedProduct = productService.updateProductDetails(productId, productUpdatePayload);

        ProductResponseDto result = modelMapper.map(updatedProduct, ProductResponseDto.class);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/admin/products/{productId}/images")
    public ResponseEntity<ProductResponseDto> updateProductImage(@PathVariable("productId") Long productId, @RequestParam("image") MultipartFile imageFile) {
        Product updatedProduct = productService.updateProductImage(productId, imageFile);

        ProductResponseDto result = modelMapper.map(updatedProduct, ProductResponseDto.class);
        return new ResponseEntity<ProductResponseDto>(result, HttpStatus.OK);
    }

}
