package com.ts.postmaster.exception;

import com.ts.postmaster.dto.ApiResp;
import com.ts.postmaster.dto.enums.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author toyewole
 */

@Slf4j
@ControllerAdvice
public class ExceptionMapper {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {

        log.debug("Debug: Error occurred ", ex);
        if (ex instanceof CustomException) {
            return handleCustomException((CustomException) ex);
        } else if (ex instanceof MethodArgumentNotValidException) {
            return handleMethodArgumentException((MethodArgumentNotValidException) ex);
        } else if (ex instanceof BadCredentialsException) {
            return handleBadCredentialException((BadCredentialsException) ex);
        } else if (ex instanceof DataIntegrityViolationException) {
            return handleDataIntegrityViolation((DataIntegrityViolationException) ex);
        }
        log.error("Error occurred ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred. Kindly contact support");
    }

    private ResponseEntity<?> handleMethodArgumentException(MethodArgumentNotValidException ex) {

        List<String> errorMessages = new ArrayList<>();
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> errors = bindingResult.getAllErrors();
        for (ObjectError error : errors) {
            String message = error.getDefaultMessage();
            errorMessages.add(message);
        }
        var resp = ApiResp.getApiResponse(ResponseEnum.ERROR, null);
        resp.setData(StringUtils.join(errorMessages, ", "));

        return ResponseEntity.badRequest().body(resp);
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
