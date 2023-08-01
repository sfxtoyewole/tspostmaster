package com.ts.postmaster.dto.enums;

import lombok.Getter;

/**
 * @author toyewole
 */
@Getter
public enum ResponseEnum {

    SUCCESS(Boolean.TRUE, "Request proccessed successfully"),
    ;

    private boolean status;
    private String message;

    ResponseEnum(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

}
