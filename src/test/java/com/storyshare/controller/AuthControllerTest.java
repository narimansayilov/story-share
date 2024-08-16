package com.storyshare.controller;

import com.storyshare.dto.request.PasswordResetRequest;
import com.storyshare.dto.request.UserLoginRequest;
import com.storyshare.dto.request.UserRegisterRequest;
import com.storyshare.dto.response.JwtResponse;
import com.storyshare.dto.response.UserResponse;
import com.storyshare.service.AuthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        UserRegisterRequest mockRegisterRequest = new UserRegisterRequest();
        mockRegisterRequest.setUsername("testuser");
        mockRegisterRequest.setPassword("Test@123");
        mockRegisterRequest.setName("John");
        mockRegisterRequest.setSurname("Doe");
        mockRegisterRequest.setEmail("test@example.com");

        UserResponse mockUserResponse = new UserResponse();
        mockUserResponse.setId(UUID.randomUUID());
        mockUserResponse.setUsername("testuser");
        mockUserResponse.setName("John");
        mockUserResponse.setSurname("Doe");
        mockUserResponse.setEmail("test@example.com");

        when(authService.register(any(UserRegisterRequest.class))).thenReturn(mockUserResponse);

        UserResponse result = authController.register(mockRegisterRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockUserResponse, result);
        Assertions.assertEquals("testuser", result.getUsername());
        Assertions.assertEquals("John", result.getName());
        Assertions.assertEquals("Doe", result.getSurname());
        Assertions.assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void testLogin() {
        UserLoginRequest mockLoginRequest = new UserLoginRequest();
        mockLoginRequest.setUsername("testuser");
        mockLoginRequest.setPassword("Test@123");

        JwtResponse mockJwtResponse = new JwtResponse("testuser", "sample_token");

        when(authService.login(any(UserLoginRequest.class))).thenReturn(mockJwtResponse);

        JwtResponse result = authController.login(mockLoginRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockJwtResponse, result);
        Assertions.assertEquals("testuser", result.getUsername());
        Assertions.assertEquals("sample_token", result.getToken());
    }

    @Test
    void testUpdatePassword() {
        String email = "test@example.com";

        authController.updatePassword(email);

        verify(authService).updatePassword(email);
    }

    @Test
    void testResetPassword() {
        PasswordResetRequest mockPasswordResetRequest = new PasswordResetRequest();
        mockPasswordResetRequest.setToken("sample_token");
        mockPasswordResetRequest.setPassword("NewPass@123");

        authController.resetPassword(mockPasswordResetRequest);

        verify(authService).resetPassword(mockPasswordResetRequest);
    }
}
