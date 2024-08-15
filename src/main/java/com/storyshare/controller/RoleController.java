package com.storyshare.controller;

import com.storyshare.dto.request.RoleRequest;
import com.storyshare.dto.response.RoleResponse;
import com.storyshare.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public void addRole(@RequestBody @Valid RoleRequest request) {
        roleService.addRole(request);
    }

    @GetMapping("/{id}")
    public RoleResponse getRole(@PathVariable UUID id) {
        return roleService.getRole(id);
    }

    @GetMapping
    public List<RoleResponse> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PutMapping("/{id}")
    public RoleResponse updateRole(@PathVariable UUID id,
                                   @RequestBody @Valid RoleRequest request) {
        return roleService.updateRole(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable UUID id) {
        roleService.deleteRole(id);
    }
}
