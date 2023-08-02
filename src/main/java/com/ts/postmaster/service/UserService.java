package com.ts.postmaster.service;

import com.ts.postmaster.dto.ApiResp;
import com.ts.postmaster.dto.SignInReq;
import com.ts.postmaster.dto.SignUpRequest;
import com.ts.postmaster.dto.enums.ResponseEnum;
import com.ts.postmaster.exception.CustomException;
import com.ts.postmaster.dao.model.PMUser;
import com.ts.postmaster.dao.repository.IPMUserRepository;
import com.ts.postmaster.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author toyewole
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final IPMUserRepository ipmUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;


    public boolean isUserExists(String username) {
        return ipmUserRepository.existsPMUserByUsername(username);
    }

    public boolean isEmailExists(String email) {
        return ipmUserRepository.existsPMUserByEmail(email);
    }

    public ApiResp<PMUser> createUser(SignUpRequest signUpRequest) {
        if (isUserExists(signUpRequest.getUsername())) {
            throw new CustomException("Username is already taken!", HttpStatus.CONFLICT);
        }

        if (isEmailExists(signUpRequest.getEmail())) {
            throw new CustomException("Email is already registered!", HttpStatus.CONFLICT);
        }

        // Creating a new user
        PMUser user = new PMUser();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setUsername(signUpRequest.getUsername());

        ipmUserRepository.save(user);

        ApiResp<PMUser> response = new ApiResp<>();
        response.setMessage("Account created successfully");
        response.setStatus(Boolean.TRUE);
        response.setData(user);

        return response;
    }


    public ApiResp<String> signIn(SignInReq signInReq) {

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInReq.getUsername(), signInReq.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        var token = jwtProvider.generateToken(authenticate);

        return ApiResp.getApiResponse(ResponseEnum.SUCCESS, token);
    }

    public UserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null; // No user authenticated
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return (UserDetails) principal;
        }

        return null;
    }


}
