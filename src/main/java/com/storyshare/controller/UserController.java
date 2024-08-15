package com.storyshare.controller;

import com.storyshare.dto.criteria.UserCriteriaRequest;
import com.storyshare.dto.request.UserUpdateRequest;
import com.storyshare.dto.response.UserResponse;
import com.storyshare.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/my")
    public UserResponse getMyProfile(){
        return userService.getUserDetails();
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable UUID id){
        return userService.getUser(id);
    }

    @GetMapping("/all")
    public List<UserResponse> getAllUsers(Pageable pageable, UserCriteriaRequest criteriaRequest) {
        return userService.getAllUsers(pageable, criteriaRequest);
    }

    @PutMapping("/update")
    public UserResponse update(@RequestPart("request") @Valid UserUpdateRequest request,
                               @RequestPart("image") MultipartFile image) {
        return userService.update(request, image);
    }

    @PatchMapping("/{id}/activate")
    public void activateUser(@PathVariable UUID id) {
        userService.activateUser(id);
    }

    @PatchMapping("/{id}/deactivate")
    public void deactivateUser(@PathVariable UUID id) {
        userService.deactivateUser(id);
    }

    @PatchMapping("/set-role")
    public void setRole(@RequestParam(name = "user") UUID userId,
                        @RequestParam(name = "role") UUID roleId) {
        userService.setRole(userId, roleId);
    }
}