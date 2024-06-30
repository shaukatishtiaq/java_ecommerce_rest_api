package com.ecom.BackendForEcommerce.utility;

import com.ecom.BackendForEcommerce.exception.customexception.CommonCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class ImageValidationUtility {
    public static void validateImage(MultipartFile file) {
        if (Objects.equals(file, null)) {
            throw new CommonCustomException(HttpStatus.BAD_REQUEST.value(), "Image key is missing.");
        }
        if (file.isEmpty()) {
            throw new CommonCustomException(HttpStatus.BAD_REQUEST.value(), "Image is missing.");
        }
        if (!Objects.equals(file.getContentType(), MediaType.IMAGE_JPEG_VALUE) && !Objects.equals(file.getContentType(), MediaType.IMAGE_PNG_VALUE)) {
            throw new CommonCustomException(HttpStatus.BAD_REQUEST.value(), "Image can only be of type " + MediaType.IMAGE_JPEG_VALUE + " or " + MediaType.IMAGE_PNG_VALUE);
        }
    }
}
