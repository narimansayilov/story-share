package com.storyshare.controller;

import com.storyshare.dto.criteria.UserCriteriaRequest;
import com.storyshare.dto.request.UserUpdateRequest;
import com.storyshare.dto.response.UserResponse;
import com.storyshare.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

class UserControllerTest {
    @Mock
    UserService userService;
    @InjectMocks
    UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMyProfile() {
        when(userService.getUserDetails()).thenReturn(new UserResponse());

        UserResponse result = userController.getMyProfile();
        Assertions.assertEquals(new UserResponse(), result);
    }

    @Test
    void testGetUser() {
        when(userService.getUser(any(UUID.class))).thenReturn(new UserResponse());

        UserResponse result = userController.getUser(new UUID(0L, 0L));
        Assertions.assertEquals(new UserResponse(), result);
    }

    @Test
    void testGetAllUsers() {
        when(userService.getAllUsers(any(Pageable.class), any(UserCriteriaRequest.class))).thenReturn(List.of(new UserResponse()));

        List<UserResponse> result = userController.getAllUsers(null, new UserCriteriaRequest());
        Assertions.assertEquals(List.of(new UserResponse()), result);
    }

    @Test
    void testUpdate() {
        when(userService.update(any(UserUpdateRequest.class), any(MultipartFile.class))).thenReturn(new UserResponse());

        UserResponse result = userController.update(new UserUpdateRequest(), null);
        Assertions.assertEquals(new UserResponse(), result);
    }

    @Test
    void testActivateUser() {
        userController.activateUser(new UUID(0L, 0L));
        verify(userService).activateUser(any(UUID.class));
    }

    @Test
    void testDeactivateUser() {
        userController.deactivateUser(new UUID(0L, 0L));
        verify(userService).deactivateUser(any(UUID.class));
    }

    @Test
    void testSetRole() {
        userController.setRole(new UUID(0L, 0L), new UUID(0L, 0L));
        verify(userService).setRole(any(UUID.class), any(UUID.class));
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme