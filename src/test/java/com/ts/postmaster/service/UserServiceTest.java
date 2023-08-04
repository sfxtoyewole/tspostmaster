package com.ts.postmaster.service;

import com.ts.postmaster.dao.model.PMUser;
import com.ts.postmaster.dao.repository.IPMUserRepository;
import com.ts.postmaster.dto.ApiResp;
import com.ts.postmaster.dto.AuthDto;
import com.ts.postmaster.dto.SignInReq;
import com.ts.postmaster.dto.SignUpRequest;
import com.ts.postmaster.exception.CustomException;
import com.ts.postmaster.security.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private IPMUserRepository ipmUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private UserService userService;


    @Test
    void testCreateUser_Success() {
        // Given
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setUsername("testUser");
        signUpRequest.setPassword("testPassword");

        when(ipmUserRepository.existsPMUserByUsername(anyString())).thenReturn(false);
        when(ipmUserRepository.existsPMUserByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(ipmUserRepository.save(any(PMUser.class))).thenReturn(new PMUser());

        // When
        ApiResp<AuthDto> response = userService.createUser(signUpRequest);

        // Then
        assertNotNull(response);
        assertTrue(response.isStatus());
        assertEquals("Account created successfully", response.getMessage());
        assertNotNull(response.getData());
        assertNotNull(response.getData().getToken());
        assertEquals("test@example.com", response.getData().getUsername());
    }

    @Test
    void testCreateUser_UsernameExists() {
        // Given
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setUsername("existingUser");
        signUpRequest.setPassword("testPassword");

        when(ipmUserRepository.existsPMUserByUsername(anyString())).thenReturn(true);

        // When and Then
        assertThrows(CustomException.class, () -> userService.createUser(signUpRequest));
    }

    @Test
    void testCreateUser_EmailExists() {
        // Given
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("existing@example.com");
        signUpRequest.setUsername("testUser");
        signUpRequest.setPassword("testPassword");

        when(ipmUserRepository.existsPMUserByEmail(anyString())).thenReturn(true);

        // When and Then
        assertThrows(CustomException.class, () -> userService.createUser(signUpRequest));
    }

    @Test
    void testSignIn_Success() {
        // Given
        SignInReq signInReq = new SignInReq();
        signInReq.setUsername("testUser");
        signInReq.setPassword("testPassword");

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtProvider.generateToken(authentication)).thenReturn("testToken");

        // When
        ApiResp<AuthDto> response = userService.signIn(signInReq);

        // Then
        assertNotNull(response);
        assertEquals("testUser", response.getData().getUsername());
        assertNotNull( response.getData().getToken());
    }

    @Test
    void testGetCurrentUser_NoAuthentication() {
        // Given
        SecurityContextHolder.clearContext();

        // When
        UserDetails userDetails = userService.getCurrentUser();

        // Then
        assertNull(userDetails);
    }

    @Test
    void testGetCurrentUser_UserDetailsFound() {
        // Given
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // When
        UserDetails result = userService.getCurrentUser();

        // Then
        assertNotNull(result);
        assertEquals(userDetails, result);
    }

    @Test
    void testGetCurrentUser_NoUserDetails() {
        // Given
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn("SomeString"); // Not an instance of UserDetails
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // When
        UserDetails userDetails = userService.getCurrentUser();

        // Then
        assertNull(userDetails);
    }
}
