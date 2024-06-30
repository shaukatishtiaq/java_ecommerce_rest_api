package com.ecom.BackendForEcommerce.exception.customexception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommonCustomException extends RuntimeException {
    private final Integer statusCode;
    private final Date timestamp;

    public CommonCustomException(Integer statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.timestamp = new Date();
    }
}
