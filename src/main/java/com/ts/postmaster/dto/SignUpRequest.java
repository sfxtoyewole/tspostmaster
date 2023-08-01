package com.ts.postmaster.dto;

import com.ts.postmaster.model.PMUser;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author toyewole
 */
@Data
public class SignUpRequest {

    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,20}$", message = "Kindly provide a valid username")
    @NotBlank(message = "Kindly provide a Username")
    private String username;

    @Email(message = "Kindly provide a valid Email Address")
    @NotBlank(message = "Kindly provide an Email Address")
    private String email;

    @NotBlank(message = "Kindly provide password")
    private String password;


}
