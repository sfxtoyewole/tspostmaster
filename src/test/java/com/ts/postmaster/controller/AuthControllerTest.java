package com.ts.postmaster.controller;

/**
 * @author toyewole
 */

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
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
        MvcResult mvcResult = mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(signUpRequest)))
                .andReturn();

        // Then
        int statusCode = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.CREATED.value(), statusCode);

        String responseContent = mvcResult.getResponse().getContentAsString();
        ApiResp<AuthDto> apiResponse = new ObjectMapper().readValue(responseContent, ApiResp.class);
        assertNotNull(apiResponse);
        assertTrue(apiResponse.isStatus());
        assertEquals(ResponseEnum.SUCCESS.getMessage(), apiResponse.getMessage());
        assertNotNull(apiResponse.getData());
        assertNotNull(apiResponse.getData().getToken());
        assertEquals("testUser", apiResponse.getData().getUsername());
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
        MvcResult mvcResult = mockMvc.perform(post("/user/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(signInReq)))
                .andReturn();

        // Then
        int statusCode = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), statusCode);

        String responseContent = mvcResult.getResponse().getContentAsString();
        ApiResp<AuthDto> apiResponse = new ObjectMapper().readValue(responseContent, ApiResp.class);
        assertNotNull(apiResponse);
        assertTrue(apiResponse.isStatus());
        assertEquals(ResponseEnum.SUCCESS.getMessage(), apiResponse.getMessage());
        assertNotNull(apiResponse.getData());
        assertNotNull(apiResponse.getData().getToken());
        assertEquals("testUser", apiResponse.getData().getUsername());

        String authToken = mvcResult.getResponse().getHeader("Authorization");
        assertEquals("testToken", authToken);
    }

    // Helper method to convert objects to JSON string
    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

