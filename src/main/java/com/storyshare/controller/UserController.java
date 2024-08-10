package com.storyshare.controller;

import com.storyshare.dto.request.UserLoginRequest;
import com.storyshare.dto.request.UserRegisterRequest;
import com.storyshare.dto.request.UserUpdateRequest;
import com.storyshare.dto.response.JwtResponse;
import com.storyshare.dto.response.UserResponse;
import com.storyshare.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody @Valid UserRegisterRequest request){
        return userService.register(request);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody @Valid UserLoginRequest request) {
        return userService.login(request);
    }

    @PutMapping("/update")
    public UserResponse update(@RequestPart("request") @Valid UserUpdateRequest request,
                               @RequestPart("image") MultipartFile image) {
        return userService.update(request, image);
    }
}
