package com.storyshare.controller;

import com.storyshare.dto.request.PasswordResetRequest;
import com.storyshare.dto.request.UserLoginRequest;
import com.storyshare.dto.request.UserRegisterRequest;
import com.storyshare.dto.response.JwtResponse;
import com.storyshare.dto.response.UserResponse;
import com.storyshare.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody @Valid UserRegisterRequest request){
        return authService.register(request);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody @Valid UserLoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/update-password")
    public void updatePassword(@RequestParam("email") String email) {
        authService.updatePassword(email);
    }

    @PutMapping("/reset-password")
    public void resetPassword(@RequestBody @Valid PasswordResetRequest request) {
        authService.resetPassword(request);
    }
}