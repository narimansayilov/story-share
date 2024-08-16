package com.storyshare.service;

import com.storyshare.dto.criteria.UserCriteriaRequest;
import com.storyshare.dto.request.UserUpdateRequest;
import com.storyshare.dto.response.UserResponse;
import com.storyshare.entity.RoleEntity;
import com.storyshare.entity.UserEntity;
import com.storyshare.exception.ActiveException;
import com.storyshare.exception.NotActiveException;
import com.storyshare.exception.NotFoundException;
import com.storyshare.mapper.UserMapper;
import com.storyshare.repository.RoleRepository;
import com.storyshare.repository.UserRepository;
import com.storyshare.service.Specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AmazonS3Service amazonS3Service;

    public UserResponse getUser(UUID id){
        UserEntity entity = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("USER_NOT_FOUND"));
        return UserMapper.INSTANCE.entityToResponse(entity);
    }

    public UserResponse getUserDetails(){
        String username = getCurrentUsername();
        UserEntity entity = userRepository.findByUsername(username).orElseThrow(() ->
                new NotFoundException("USER_NOT_FOUND"));
        return UserMapper.INSTANCE.entityToResponse(entity);
    }

    public List<UserResponse> getAllUsers(Pageable pageable, UserCriteriaRequest criteriaRequest){
        Specification<UserEntity> specification = UserSpecification.getUserByCriteria(criteriaRequest);
        Page<UserEntity> entities = userRepository.findAll(specification, pageable);
        return UserMapper.INSTANCE.entitiesToResponses(entities);
    }

    public UserResponse update(UserUpdateRequest request, MultipartFile image){
        UserEntity entity = userRepository.findByUsername(getCurrentUsername()).orElseThrow(() ->
                new NotFoundException("USER_NOT_FOUND"));
        entity.setPhotoUrl(amazonS3Service.uploadFile(image));
        UserMapper.INSTANCE.mapRequestToEntity(entity, request);
        userRepository.save(entity);
        return UserMapper.INSTANCE.entityToResponse(entity);
    }
    public void activateUser(UUID id){
        UserEntity entity = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("USER_NOT_FOUND"));
        if(entity.getStatus()){
            throw new ActiveException("USER_ALREADY_ACTIVE");
        }
        entity.setStatus(true);
        userRepository.save(entity);
    }

    public void deactivateUser(UUID id){
        UserEntity entity = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("USER_NOT_FOUND"));
        if(!entity.getStatus()){
            throw new NotActiveException("USER_NOT_ACTIVE");
        }
        entity.setStatus(false);
        userRepository.save(entity);
    }

    public void setRole(UUID userId, UUID roleId){
        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("USER_NOT_FOUND"));
        RoleEntity role = roleRepository.findById(roleId).orElseThrow(() ->
                new NotFoundException("ROLE_NOT_FOUND"));
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    public String getCurrentUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}