package com.storyshare.service;

import com.storyshare.dto.request.RoleRequest;
import com.storyshare.dto.response.RoleResponse;
import com.storyshare.entity.RoleEntity;
import com.storyshare.exception.NotFoundException;
import com.storyshare.mapper.RoleMapper;
import com.storyshare.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public void addRole(RoleRequest request) {
        roleRepository.save(RoleMapper.INSTANCE.requestToEntity(request));
    }

    public RoleResponse getRole(UUID id) {
        RoleEntity entity = roleRepository.findById(id).orElseThrow(() ->
                new NotFoundException("ROLE_NOT_FOUND"));
        return RoleMapper.INSTANCE.entityToResponse(entity);
    }

    public List<RoleResponse> getAllRoles() {
        return RoleMapper.INSTANCE.entitiesToResponses(roleRepository.findAll());
    }

    public RoleResponse updateRole(UUID id, RoleRequest request) {
        RoleEntity entity = roleRepository.findById(id).orElseThrow(() ->
                new NotFoundException("ROLE_NOT_FOUND"));
        RoleMapper.INSTANCE.mapRequestToEntity(entity, request);
        roleRepository.save(entity);
        return RoleMapper.INSTANCE.entityToResponse(entity);
    }

    public void deleteRole(UUID id) {
        roleRepository.deleteById(id);
    }
}