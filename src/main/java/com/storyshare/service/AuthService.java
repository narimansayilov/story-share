package com.storyshare.service;

import com.amazonaws.services.cloudformation.model.AlreadyExistsException;
import com.storyshare.dto.request.PasswordResetRequest;
import com.storyshare.dto.request.UserLoginRequest;
import com.storyshare.dto.request.UserRegisterRequest;
import com.storyshare.dto.response.JwtResponse;
import com.storyshare.dto.response.UserResponse;
import com.storyshare.entity.RoleEntity;
import com.storyshare.entity.UserEntity;
import com.storyshare.entity.VerificationTokenEntity;
import com.storyshare.enums.VerificationType;
import com.storyshare.exception.NotFoundException;
import com.storyshare.exception.TokenExpiredException;
import com.storyshare.mapper.UserMapper;
import com.storyshare.repository.RoleRepository;
import com.storyshare.repository.UserRepository;
import com.storyshare.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final VerificationService verificationService;
    private final AuthenticationManager authenticationManager;

    public JwtResponse login(UserLoginRequest request) {
        userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND"));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User principal = (User) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(principal);
        return new JwtResponse(request.getUsername(), accessToken);
    }

    public UserResponse register(UserRegisterRequest request) {
        userRepository.findByUsername(request.getUsername()).ifPresent(user -> {
            throw new AlreadyExistsException("USERNAME_ALREADY_EXISTS");
        });
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new AlreadyExistsException("EMAIL_ALREADY_EXISTS");
        });
        UserEntity entity = UserMapper.INSTANCE.registerRequestToEntity(request);
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        entity.setRoles(List.of(getRole()));
        userRepository.save(entity);
        verificationService.generateAndSendToken(entity.getEmail(), VerificationType.EMAIL_VERIFICATION);
        return UserMapper.INSTANCE.entityToResponse(entity);
    }

    public void updatePassword(String email){
        userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND"));
        verificationService.generateAndSendToken(email, VerificationType.PASSWORD_RESET);
    }

    public void resetPassword(PasswordResetRequest request){
        VerificationTokenEntity verificationToken = verificationTokenRepository.findByToken(request.getToken()).orElseThrow(() ->
                new NotFoundException("VERIFICATION_TOKEN_NOT_FOUND"));
        if (verificationToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("TOKEN_EXPIRED");
        }
        UserEntity user = userRepository.findByEmail(verificationToken.getEmail())
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND"));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    private RoleEntity getRole() {
        return roleRepository.findByName("USER")
                .orElseGet(() -> roleRepository.save(RoleEntity.builder().name("USER").build()));
    }
}
