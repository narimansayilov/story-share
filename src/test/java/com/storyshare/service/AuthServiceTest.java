package com.storyshare.service;

import com.storyshare.dto.request.PasswordResetRequest;
import com.storyshare.dto.request.UserLoginRequest;
import com.storyshare.dto.request.UserRegisterRequest;
import com.storyshare.dto.response.JwtResponse;
import com.storyshare.dto.response.UserResponse;
import com.storyshare.entity.RoleEntity;
import com.storyshare.entity.UserEntity;
import com.storyshare.entity.VerificationTokenEntity;
import com.storyshare.enums.VerificationType;
import com.storyshare.repository.RoleRepository;
import com.storyshare.repository.UserRepository;
import com.storyshare.repository.VerificationTokenRepository;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private VerificationTokenRepository verificationTokenRepository;

    @Mock
    private VerificationService verificationService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private EnhancedRandom random;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();
    }

//    @Test
//    void testLogin_Success() {
//        // Given
//        UserLoginRequest request = random.nextObject(UserLoginRequest.class);
//        UserEntity userEntity = random.nextObject(UserEntity.class);
//
//        // When
//        when(userRepository.findByUsername(eq(request.getUsername()))).thenReturn(Optional.of(userEntity));
//        Authentication authentication = mock(Authentication.class);
//        when(authentication.getPrincipal()).thenReturn(userEntity);
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
//        when(jwtService.generateAccessToken(any(UserDetails.class))).thenReturn("accessToken");
//
//        // Then
//        JwtResponse response = authService.login(request);
//
//        // And
//        assertEquals(request.getUsername(), response.getUsername());
//        assertEquals("accessToken", response.getToken());
//        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
//        verify(jwtService).generateAccessToken(any(UserDetails.class));
//    }

    @Test
    void testRegister_Success() {
        // Given
        UserRegisterRequest request = random.nextObject(UserRegisterRequest.class);
        UserEntity userEntity = random.nextObject(UserEntity.class);

        // When
        when(userRepository.findByUsername(eq(request.getUsername()))).thenReturn(Optional.empty());
        when(userRepository.findByEmail(eq(request.getEmail()))).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleRepository.findByName(eq("USER"))).thenReturn(Optional.empty());
        when(roleRepository.save(any(RoleEntity.class))).thenReturn(RoleEntity.builder().name("USER").build());
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        // Then
        UserResponse response = authService.register(request);

        // And
        assertNotNull(response);
        verify(userRepository).findByUsername(eq(request.getUsername()));
        verify(userRepository).findByEmail(eq(request.getEmail()));
        verify(passwordEncoder).encode(eq(request.getPassword()));
        verify(userRepository).save(any(UserEntity.class));
        verify(verificationService).generateAndSendToken(eq(request.getEmail()), eq(VerificationType.EMAIL_VERIFICATION));
    }

    @Test
    void testUpdatePassword_Success() {
        // Given
        String email = "test@example.com";
        UserEntity userEntity = random.nextObject(UserEntity.class);

        // When
        when(userRepository.findByEmail(eq(email))).thenReturn(Optional.of(userEntity));

        // Then
        authService.updatePassword(email);

        // And
        verify(userRepository).findByEmail(eq(email));
        verify(verificationService).generateAndSendToken(eq(email), eq(VerificationType.PASSWORD_RESET));
    }

    @Test
    void testResetPassword_Success() {
        // Given
        PasswordResetRequest request = random.nextObject(PasswordResetRequest.class);
        VerificationTokenEntity tokenEntity = random.nextObject(VerificationTokenEntity.class);
        tokenEntity.setExpirationDate(LocalDateTime.now().plusHours(1));

        // When
        when(verificationTokenRepository.findByToken(eq(request.getToken()))).thenReturn(Optional.of(tokenEntity));
        when(userRepository.findByEmail(eq(tokenEntity.getEmail()))).thenReturn(Optional.of(new UserEntity()));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Then
        authService.resetPassword(request);

        // And
        verify(verificationTokenRepository).findByToken(eq(request.getToken()));
        verify(userRepository).findByEmail(eq(tokenEntity.getEmail()));
        verify(passwordEncoder).encode(eq(request.getPassword()));
        verify(userRepository).save(any(UserEntity.class));
    }
}
