package com.storyshare.service;

import com.amazonaws.services.cloudformation.model.AlreadyExistsException;
import com.storyshare.dto.request.UserLoginRequest;
import com.storyshare.dto.request.UserRegisterRequest;
import com.storyshare.dto.request.UserUpdateRequest;
import com.storyshare.dto.response.JwtResponse;
import com.storyshare.dto.response.UserResponse;
import com.storyshare.entity.RoleEntity;
import com.storyshare.entity.UserEntity;
import com.storyshare.exception.NotFoundException;
import com.storyshare.mapper.UserMapper;
import com.storyshare.repository.RoleRepository;
import com.storyshare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AmazonS3Service amazonS3Service;
    private final AuthenticationManager authenticationManager;
    private final VerificationService verificationService;

    public UserResponse register(UserRegisterRequest request){
        userRepository.findByUsername(request.getUsername()).ifPresent(user -> {
            throw  new AlreadyExistsException("USERNAME_ALREADY_EXISTS");
        });
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            throw  new AlreadyExistsException("EMAIL_ALREADY_EXISTS");
        });
        UserEntity entity = UserMapper.INSTANCE.registerRequestToEntity(request);
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        entity.setRoles(List.of(getRole()));
        userRepository.save(entity);
        verificationService.generateAndSendVerificationToken(entity.getEmail());
        return UserMapper.INSTANCE.entityToResponse(entity);
    }

    public JwtResponse login(UserLoginRequest request){
        userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new NotFoundException("USER_NOT_FOUND"));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User principal = (User) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(principal);
        return new JwtResponse(request.getUsername(), accessToken);
    }

    public UserResponse getUser(UUID id){
        UserEntity entity = userRepository.findById(id).orElseThrow(() -> new NotFoundException("USER_NOT_FOUND"));
        return UserMapper.INSTANCE.entityToResponse(entity);
    }

    public UserResponse getUserDetails(){
        String username = getCurrentUsername();
        UserEntity entity = userRepository.findByUsername(username).orElseThrow(() ->
                new NotFoundException("USER_NOT_FOUND"));
        return UserMapper.INSTANCE.entityToResponse(entity);
    }

    public UserResponse update(UserUpdateRequest request, MultipartFile image){
        UserEntity entity = userRepository.findByUsername(getCurrentUsername()).orElseThrow(() -> new NotFoundException("USER_NOT_FOUND"));
        entity.setPhotoUrl(amazonS3Service.uploadFile(image));
        UserMapper.INSTANCE.mapRequestToEntity(entity, request);
        userRepository.save(entity);
        return UserMapper.INSTANCE.entityToResponse(entity);
    }

    public String getCurrentUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    private RoleEntity getRole() {
        return roleRepository.findByName("USER")
                .orElseGet(() -> roleRepository.save(RoleEntity.builder().name("USER").build()));
    }
}