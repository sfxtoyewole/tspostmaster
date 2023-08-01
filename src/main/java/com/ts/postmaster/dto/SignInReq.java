package com.ts.postmaster.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author toyewole
 */
@Data
public class SignInReq {
    @NotBlank(message = "Kindly provide a Username or Email")
    private String username;

    @NotBlank(message = "Kindly provide password")
    private String password;

}
