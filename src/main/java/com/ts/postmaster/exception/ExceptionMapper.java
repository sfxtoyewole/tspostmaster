package com.ts.postmaster.exception;

import com.ts.postmaster.dto.ApiResp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author toyewole
 */

@ControllerAdvice
public class ExceptionMapper {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {

        if (ex instanceof CustomException) {
            return handleCustomException((CustomException) ex);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred. Kindly contact support");
    }

    private ResponseEntity<?> handleCustomException(CustomException ex) {
        var resp = new ApiResp<>();
        resp.setStatus(false);
        resp.setMessage(ex.getMessage());

        return ResponseEntity.status(ex.getHttpStatus()).body(resp);
    }


}
