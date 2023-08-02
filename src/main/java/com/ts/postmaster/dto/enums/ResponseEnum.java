package com.ts.postmaster.dto.enums;

import lombok.Getter;

/**
 * @author toyewole
 */
@Getter
public enum ResponseEnum {

    SUCCESS(Boolean.TRUE, "Request processed successfully"),
    ERROR(Boolean.FALSE, "Error occurred processing request"),
    ;

    private boolean status;
    private String message;

    ResponseEnum(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

}
