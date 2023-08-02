package com.ts.postmaster.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author toyewole
 */
@Data
public class SignUpRequest {


    @ApiModelProperty(value = "Username", example = "tester")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,20}$", message = "Kindly provide a valid username")
    @NotBlank(message = "Kindly provide a Username")
    private String username;


    @ApiModelProperty(value = "Email", example = "tester123@gmail.com")
    @Email(message = "Kindly provide a valid Email Address")
    @NotBlank(message = "Kindly provide an Email Address")
    private String email;

    @ApiModelProperty(value = "password", example = "8a20ec812ea0")
    @NotBlank(message = "Kindly provide password")
    private String password;


}
