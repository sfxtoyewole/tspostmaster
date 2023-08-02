package com.ts.postmaster.controller;

import com.ts.postmaster.dto.ApiResp;
import com.ts.postmaster.dto.SignInReq;
import com.ts.postmaster.dto.SignUpRequest;
import com.ts.postmaster.dao.model.PMUser;
import com.ts.postmaster.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author toyewole
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class AuthController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public ApiResp<PMUser> signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        return userService.createUser(signUpRequest);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignInReq signInReq) {
        var resp = userService.signIn(signInReq);
        HttpHeaders headers = new HttpHeaders();

        // Add one or more headers using the HttpHeaders methods
        headers.add("Authorization", resp.getData());
        return new ResponseEntity<>(resp, headers, HttpStatus.OK);
    }


}
