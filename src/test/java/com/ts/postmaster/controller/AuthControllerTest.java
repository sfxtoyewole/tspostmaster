package com.ts.postmaster.controller;

/**
 * @author toyewole
 */

import com.google.gson.Gson;
import com.ts.postmaster.dto.ApiResp;
import com.ts.postmaster.dto.AuthDto;
import com.ts.postmaster.dto.SignInReq;
import com.ts.postmaster.dto.SignUpRequest;
import com.ts.postmaster.dto.enums.ResponseEnum;
import com.ts.postmaster.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;
    private static final Gson gson = new Gson();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testSignUp_Success() throws Exception {
        // Given
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setUsername("testUser");
        signUpRequest.setPassword("testPassword");

        AuthDto authDto = new AuthDto();
        authDto.setToken("testToken");
        authDto.setUsername("testUser");

        ApiResp<AuthDto> response = ApiResp.getApiResponse(ResponseEnum.SUCCESS, authDto);

        when(userService.createUser(any())).thenReturn(response);

        // When
        ResultActions result = mockMvc.perform(post("/access/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(signUpRequest)));

        // Then

        result.andExpect(MockMvcResultMatchers.status().isCreated());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.status").isBoolean());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.data.token").isNotEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value("testUser"));

    }

    @Test
    void testSignIn_Success() throws Exception {
        // Given
        SignInReq signInReq = new SignInReq();
        signInReq.setUsername("testUser");
        signInReq.setPassword("testPassword");

        AuthDto authDto = new AuthDto();
        authDto.setToken("testToken");
        authDto.setUsername("testUser");
        ApiResp<AuthDto> response = ApiResp.getApiResponse(ResponseEnum.SUCCESS, authDto);

        when(userService.signIn(any())).thenReturn(response);

        // When
        ResultActions result = mockMvc.perform(post("/access/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(signInReq)));

        // The
        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.status").isBoolean());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.data.token").isNotEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value("testUser"));

        String authToken = result.andReturn().getResponse().getHeader("Authorization");
        assertEquals("testToken", authToken);
    }


}

