package com.ecom.BackendForEcommerce.service.impl;

import com.ecom.BackendForEcommerce.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {
    @Value("${image.storage.upload.key}")
    private String apikey;

    @Value("${image.storage.upload.url}")
    private String url;

    @Override
    public String uploadImage(MultipartFile imageFile) {

        RestTemplate restTemplate = new RestTemplate();

        //Request body containing image and api key.
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        try {
            body.add("key", apikey);
            body.add("name", UUID.randomUUID().toString());
            body.add("image", Base64.getEncoder().encode(imageFile.getBytes()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Request header setting content type ot multipart
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);


        //Creating a HttpEntity to send as request body
        HttpEntity<MultiValueMap<String, Object>> requestBody = new HttpEntity<>(body, headers);

        //Post request to upload image
        ResponseEntity<Map> response = restTemplate.postForEntity(url, requestBody, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseBody = (Map<String, Object>) response.getBody();

            if (!Objects.isNull(responseBody) && (Boolean) responseBody.get("success")) {
                Map<String, Object> data = (Map<String, Object>) responseBody.get("data");

                return data.get("display_url").toString();
            } else {
                throw new RuntimeException("Image upload failed: " + responseBody);
            }
        } else {
            throw new RuntimeException("Failed to upload image, status code: " + response.getStatusCode());
        }
    }
}
