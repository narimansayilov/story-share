package com.storyshare.controller;

import com.storyshare.dto.criteria.UserCriteriaRequest;
import com.storyshare.dto.request.UserUpdateRequest;
import com.storyshare.dto.response.UserResponse;
import com.storyshare.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UUID userId;
    private UserResponse userResponse;
    private UserUpdateRequest userUpdateRequest;
    private UserCriteriaRequest userCriteriaRequest;
    private MultipartFile mockFile;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userId = UUID.randomUUID();

        userResponse = new UserResponse();
        userResponse.setId(userId);
        userResponse.setUsername("sampleUser");
        userResponse.setName("John");
        userResponse.setSurname("Doe");
        userResponse.setEmail("john.doe@example.com");
        userResponse.setStoryCount(10);
        userResponse.setPhotoUrl("http://example.com/photo.jpg");

        userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setUsername("newUsername");
        userUpdateRequest.setName("Jane");
        userUpdateRequest.setSurname("Smith");

        userCriteriaRequest = new UserCriteriaRequest();
        userCriteriaRequest.setUsername("sampleUser");

        mockFile = new MockMultipartFile("image", "image.jpg", "image/jpeg", "some-image".getBytes());

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void testGetMyProfile() {
        when(userService.getUserDetails()).thenReturn(userResponse);

        UserResponse result = userController.getMyProfile();

        assertEquals(userResponse, result);
        verify(userService).getUserDetails();
    }

    @Test
    void testGetUser() {
        when(userService.getUser(any(UUID.class))).thenReturn(userResponse);

        UserResponse result = userController.getUser(userId);

        assertEquals(userResponse, result);
        assertEquals(userId, result.getId());
        verify(userService).getUser(userId);
    }

    @Test
    void testGetAllUsers() {
        when(userService.getAllUsers(any(Pageable.class), any(UserCriteriaRequest.class)))
                .thenReturn(List.of(userResponse));

        List<UserResponse> result = userController.getAllUsers(pageable, userCriteriaRequest);

        assertEquals(1, result.size());
        assertEquals(userResponse, result.get(0));
        verify(userService).getAllUsers(pageable, userCriteriaRequest);
    }

    @Test
    void testUpdate() {
        when(userService.update(any(UserUpdateRequest.class), any(MultipartFile.class)))
                .thenReturn(userResponse);

        UserResponse result = userController.update(userUpdateRequest, mockFile);

        assertEquals(userResponse, result);
        verify(userService).update(userUpdateRequest, mockFile);
    }

    @Test
    void testActivateUser() {
        userController.activateUser(userId);

        verify(userService).activateUser(userId);
    }

    @Test
    void testDeactivateUser() {
        userController.deactivateUser(userId);

        verify(userService).deactivateUser(userId);
    }

    @Test
    void testSetRole() {
        UUID roleId = UUID.randomUUID();

        userController.setRole(userId, roleId);

        verify(userService).setRole(userId, roleId);
    }
}