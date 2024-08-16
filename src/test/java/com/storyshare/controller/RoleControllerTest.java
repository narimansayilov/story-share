package com.storyshare.controller;

import com.storyshare.dto.request.RoleRequest;
import com.storyshare.dto.response.RoleResponse;
import com.storyshare.service.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

class RoleControllerTest {
    @Mock
    RoleService roleService;
    @InjectMocks
    RoleController roleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddRole() {
        roleController.addRole(new RoleRequest());
        verify(roleService).addRole(any(RoleRequest.class));
    }

    @Test
    void testGetRole() {
        when(roleService.getRole(any(UUID.class))).thenReturn(new RoleResponse());

        RoleResponse result = roleController.getRole(new UUID(0L, 0L));
        Assertions.assertEquals(new RoleResponse(), result);
    }

    @Test
    void testGetAllRoles() {
        when(roleService.getAllRoles()).thenReturn(List.of(new RoleResponse()));

        List<RoleResponse> result = roleController.getAllRoles();
        Assertions.assertEquals(List.of(new RoleResponse()), result);
    }

    @Test
    void testUpdateRole() {
        when(roleService.updateRole(any(UUID.class), any(RoleRequest.class))).thenReturn(new RoleResponse());

        RoleResponse result = roleController.updateRole(new UUID(0L, 0L), new RoleRequest());
        Assertions.assertEquals(new RoleResponse(), result);
    }

    @Test
    void testDeleteRole() {
        roleController.deleteRole(new UUID(0L, 0L));
        verify(roleService).deleteRole(any(UUID.class));
    }
}