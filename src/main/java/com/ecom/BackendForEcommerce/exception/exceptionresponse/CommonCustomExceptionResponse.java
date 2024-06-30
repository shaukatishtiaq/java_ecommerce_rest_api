package com.ecom.BackendForEcommerce.exception.exceptionresponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommonCustomExceptionResponse {
    private Integer statusCode;
    private String message;
    //    private String details;
    private Date timestamp;

}
