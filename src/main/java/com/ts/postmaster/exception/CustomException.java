package com.ts.postmaster.exception;

import org.springframework.http.HttpStatus;

/**
 * @author toyewole
 */
public class CustomException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }


}
