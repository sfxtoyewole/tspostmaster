package com.ts.postmaster.exception;

import com.ts.postmaster.dto.ApiResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author toyewole
 */

@Slf4j
@ControllerAdvice
public class ExceptionMapper {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        log.error("Error occurred ", ex);

        if (ex instanceof CustomException) {
            return handleCustomException((CustomException) ex);
        }else if (ex instanceof CredentialsExpiredException){
            return handleCredentialExpiredException((CredentialsExpiredException) ex);
        } else if (ex instanceof BadCredentialsException) {
            return handleBadCredentialException((BadCredentialsException) ex);
        }else if (ex instanceof LockedException){
            return handleLockoutException((LockedException) ex);
        } else if (ex instanceof DataIntegrityViolationException) {
            return handleDataIntegrityViolation((DataIntegrityViolationException) ex);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred. Kindly contact support");
    }

    private ResponseEntity<?> handleCredentialExpiredException(CredentialsExpiredException ex) {
        var resp = new ApiResp<>();
        resp.setStatus(false);
        resp.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resp);
    }

    private ResponseEntity<?> handleLockoutException(LockedException ex) {
        var resp = new ApiResp<>();
        resp.setStatus(false);
        resp.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resp);

    }

    private ResponseEntity<?> handleBadCredentialException(BadCredentialsException ex) {
        var resp = new ApiResp<>();
        resp.setStatus(false);
        resp.setMessage("Invalid username or password ");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resp);
    }

    private ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        var resp = new ApiResp<>();
        resp.setStatus(false);
        resp.setMessage("There is a data conflict. Kindly confirm and try again or contact support");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
    }

    private ResponseEntity<?> handleCustomException(CustomException ex) {
        var resp = new ApiResp<>();
        resp.setStatus(false);
        resp.setMessage(ex.getMessage());

        return ResponseEntity.status(ex.getHttpStatus()).body(resp);
    }


}
