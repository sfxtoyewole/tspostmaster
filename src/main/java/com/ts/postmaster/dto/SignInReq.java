package com.ts.postmaster.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author toyewole
 */
@Data
public class SignInReq {

    @ApiModelProperty(value = "Username or email", example = "test123@gmail.com")
    @NotBlank(message = "Kindly provide a Username or Email")
    private String username;

    @ApiModelProperty(value = "Password", example = "8a20ec812ea0")
    @NotBlank(message = "Kindly provide password")
    private String password;

}
