package com.ts.postmaster.dto;

import com.ts.postmaster.dto.enums.ResponseEnum;
import lombok.Data;

/**
 * @author toyewole
 */

@Data
public class ApiResp<T> {
    private boolean status;
    private String message;
    private T data;

    public ApiResp(){}

    public static ApiResp<?> getApiResponse(ResponseEnum responseEnum) {
        var resp = new ApiResp<>();
        resp.setMessage(resp.getMessage());
        resp.setStatus(responseEnum.isStatus());

        return resp;
    }

    public static <T> ApiResp<T> getApiResponse(ResponseEnum responseEnum, T data) {
        var resp = new ApiResp<T>();
        resp.setMessage(responseEnum.getMessage());
        resp.setStatus(responseEnum.isStatus());
        resp.setData(data);

        return resp;
    }



}
