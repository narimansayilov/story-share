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

import static org.mockito.Mockito.*;

class AuthControllerTest {
    @Mock
    AuthService authService;
    @InjectMocks
    AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        when(authService.register(any(UserRegisterRequest.class))).thenReturn(new UserResponse());

        UserResponse result = authController.register(new UserRegisterRequest());
        Assertions.assertEquals(new UserResponse(), result);
    }

    @Test
    void testLogin() {
        when(authService.login(any(UserLoginRequest.class))).thenReturn(new JwtResponse("username", "token"));

        JwtResponse result = authController.login(new UserLoginRequest());
        Assertions.assertEquals(new JwtResponse("username", "token"), result);
    }

    @Test
    void testUpdatePassword() {
        authController.updatePassword("email");
        verify(authService).updatePassword(anyString());
    }

    @Test
    void testResetPassword() {
        authController.resetPassword(new PasswordResetRequest());
        verify(authService).resetPassword(any(PasswordResetRequest.class));
    }
}