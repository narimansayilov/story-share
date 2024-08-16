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
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddRole() {
        RoleRequest mockRoleRequest = new RoleRequest();
        mockRoleRequest.setName("Admin");

        roleController.addRole(mockRoleRequest);

        verify(roleService).addRole(any(RoleRequest.class));
    }

    @Test
    void testGetRole() {
        UUID roleId = UUID.randomUUID();
        RoleResponse mockRoleResponse = new RoleResponse();
        mockRoleResponse.setId(roleId);
        mockRoleResponse.setName("Admin");

        when(roleService.getRole(any(UUID.class))).thenReturn(mockRoleResponse);

        RoleResponse result = roleController.getRole(roleId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockRoleResponse, result);
        Assertions.assertEquals(roleId, result.getId());
        Assertions.assertEquals("Admin", result.getName());
    }

    @Test
    void testGetAllRoles() {
        RoleResponse mockRoleResponse = new RoleResponse();
        mockRoleResponse.setId(UUID.randomUUID());
        mockRoleResponse.setName("User");

        when(roleService.getAllRoles()).thenReturn(List.of(mockRoleResponse));

        List<RoleResponse> result = roleController.getAllRoles();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(mockRoleResponse, result.get(0));
    }

    @Test
    void testUpdateRole() {
        UUID roleId = UUID.randomUUID();
        RoleRequest mockRoleRequest = new RoleRequest();
        mockRoleRequest.setName("Manager");

        RoleResponse mockRoleResponse = new RoleResponse();
        mockRoleResponse.setId(roleId);
        mockRoleResponse.setName("Manager");

        when(roleService.updateRole(any(UUID.class), any(RoleRequest.class))).thenReturn(mockRoleResponse);

        RoleResponse result = roleController.updateRole(roleId, mockRoleRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockRoleResponse, result);
        Assertions.assertEquals(roleId, result.getId());
        Assertions.assertEquals("Manager", result.getName());
    }

    @Test
    void testDeleteRole() {
        UUID roleId = UUID.randomUUID();

        roleController.deleteRole(roleId);

        verify(roleService).deleteRole(any(UUID.class));
    }
}