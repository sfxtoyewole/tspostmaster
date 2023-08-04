package com.ts.postmaster.controller;

import com.google.gson.Gson;
import com.ts.postmaster.dao.model.PMUser;
import com.ts.postmaster.dao.repository.IPMUserRepository;
import com.ts.postmaster.dto.SignUpRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

/**
 * @author toyewole
 */
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerIntegrationTest extends AbstractIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IPMUserRepository ipmUserRepository;
    private static final Gson gson = new Gson();

    @Test
     void givenValidRequest_whenSigningUp_shouldReturnSignUpToken() throws Exception {

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("test@gmail.com");
        signUpRequest.setUsername("username");
        signUpRequest.setPassword("password");

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/access/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(signUpRequest)));


        result.andExpect(MockMvcResultMatchers.status().isCreated());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.status").isBoolean());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.data.token").isNotEmpty());

        Optional<PMUser> optionalPMUser = ipmUserRepository.findPMUserByEmailOrUsername(signUpRequest.getEmail());

        Assertions.assertTrue(optionalPMUser.isPresent());

    }

    @Test
    void givenValidRequest_whenSigningIn_shouldReturnSignUpToken() throws Exception {

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("test@gmail.com");
        signUpRequest.setUsername("username");
        signUpRequest.setPassword("password");

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/access/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(signUpRequest)));


        result.andExpect(MockMvcResultMatchers.status().isCreated());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.status").isBoolean());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.data.token").isNotEmpty());

        Optional<PMUser> optionalPMUser = ipmUserRepository.findPMUserByEmailOrUsername(signUpRequest.getEmail());

        Assertions.assertTrue(optionalPMUser.isPresent());

    }

}
