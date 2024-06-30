package com.ecom.BackendForEcommerce.exception.exceptionhandler;

import com.ecom.BackendForEcommerce.exception.customexception.CommonCustomException;
import com.ecom.BackendForEcommerce.exception.exceptionresponse.CommonCustomExceptionResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> dtoValidationExceptionHandler(MethodArgumentNotValidException e) {

        Map<String, Object> fieldErrors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach(
                error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();

                    fieldErrors.put(fieldName, errorMessage);
                }
        );

        Map<String, Object> expResponse = new HashMap<>();
        expResponse.put("message", fieldErrors);
        expResponse.put("statusCode", e.getStatusCode().value());

        return new ResponseEntity<>(expResponse, e.getStatusCode());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> invalidCredentialsDuringLoginExceptionHandler(BadCredentialsException e) {
        Map<String, Object> expResponse = new HashMap<>();

        expResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
        expResponse.put("message", "Invalid Credentials");

        return new ResponseEntity<>(expResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<Map<String, Object>> internalAuthenticationServiceExceptionHandler(InternalAuthenticationServiceException e) {
        Map<String, Object> expResponse = new HashMap<>();

        expResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
        expResponse.put("message", "Invalid Credentials");

        return new ResponseEntity<>(expResponse, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(CommonCustomException.class)
    public ResponseEntity<CommonCustomExceptionResponse> commonCustomExceptionHandler(CommonCustomException ex) {

        CommonCustomExceptionResponse expResponse = new CommonCustomExceptionResponse();

        expResponse.setStatusCode(ex.getStatusCode());
        expResponse.setMessage(ex.getMessage());
        expResponse.setTimestamp(new Date());

        return new ResponseEntity<>(expResponse, HttpStatusCode.valueOf(expResponse.getStatusCode()));
    }

    //    Multipart product upload exception image not found.
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<Map<String, Object>> missingServletRequestPartExceptionHandler(MissingServletRequestPartException e) {

        Map<String, Object> expResponse = new HashMap<>();

        expResponse.put("statusCode", HttpStatus.BAD_REQUEST.value());
        expResponse.put("message", e.getMessage());
        return new ResponseEntity<>(expResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, Object>> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        Map<String, Object> expResponse = new HashMap<>();
        expResponse.put("statusCode", e.getStatusCode().value());
        expResponse.put("message", "Missing parameter " + e.getParameterName() + ".");
        return new ResponseEntity<>(expResponse, e.getStatusCode());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> constraintViolationException(ConstraintViolationException e) {
        Map<String, Object> expResponse = new HashMap<>();
        expResponse.put("statusCode", HttpStatus.BAD_REQUEST.value());
        expResponse.put("message", e.getMessage());
        return new ResponseEntity<>(expResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> defaultExceptionHandler(Exception e) {
        Map<String, Object> expResponse = new HashMap<>();
        expResponse.put("message", "Error occured. Sorry for inconvenience.");
        expResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(expResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
