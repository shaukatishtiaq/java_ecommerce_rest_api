package com.ecom.BackendForEcommerce.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {
    String uploadImage(MultipartFile imageFile);
}
