//package com.storyshare.service;
//
//import com.storyshare.entity.UserEntity;
//import com.storyshare.entity.VerificationTokenEntity;
//import com.storyshare.repository.UserRepository;
//import com.storyshare.repository.VerificationTokenRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.mail.MailException;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.hibernate.validator.internal.util.Contracts.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//class VerificationServiceTest {
//
//    @Mock
//    private VerificationTokenRepository tokenRepository;
//
//    @Mock
//    private EmailService emailService;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private VerificationService verificationService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void generateAndSendVerificationToken_Success() {
//        String email = "test@example.com";
//
//        when(tokenRepository.save(any(VerificationTokenEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
//        doNothing().when(emailService).sendVerificationEmail(anyString(), anyString());
//
//        verificationService.generateAndSendVerificationToken(email);
//
//        ArgumentCaptor<String> tokenCaptor = ArgumentCaptor.forClass(String.class);
//        verify(emailService).sendVerificationEmail(eq(email), tokenCaptor.capture());
//
//        String actualToken = tokenCaptor.getValue();
//        assertTrue(UUID.fromString(actualToken) != null, "Token should be a valid UUID.");
//    }
//
//
//    @Test
//    void generateAndSendVerificationToken_Failure_EmailSendingFails() {
//        String email = "test@example.com";
//        String token = "c0d1c3f3-9d87-4e52-af31-76d004c46893";
//        LocalDateTime expirationDate = LocalDateTime.now().plusHours(1);
//
//        when(tokenRepository.save(any(VerificationTokenEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
//        doThrow(new MailException("Email sending failed") {}).when(emailService).sendVerificationEmail(anyString(), anyString());
//
//        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
//            verificationService.generateAndSendVerificationToken(email);
//        });
//
//        assertEquals("Failed to send verification email.", thrown.getMessage());
//    }
//
//    @Test
//    void verifyEmail_Success() {
//        String token = "valid-token";
//        String email = "test@example.com";
//
//        VerificationTokenEntity tokenEntity = new VerificationTokenEntity();
//        tokenEntity.setToken(token);
//        tokenEntity.setEmail(email);
//        tokenEntity.setExpirationDate(LocalDateTime.now().plusHours(1));
//
//        when(tokenRepository.findByToken(token)).thenReturn(Optional.of(tokenEntity));
//        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new UserEntity()));
//
//        String result = verificationService.verifyEmail(token);
//
//        assertEquals("Email successfully verified!", result);
//    }
//
//    @Test
//    void verifyEmail_TokenExpired() {
//        String token = "expired-token";
//        String email = "test@example.com";
//
//        VerificationTokenEntity tokenEntity = new VerificationTokenEntity();
//        tokenEntity.setToken(token);
//        tokenEntity.setEmail(email);
//        tokenEntity.setExpirationDate(LocalDateTime.now().minusHours(1));
//
//        when(tokenRepository.findByToken(token)).thenReturn(Optional.of(tokenEntity));
//
//        String result = verificationService.verifyEmail(token);
//
//        assertEquals("Invalid or expired token.", result);
//    }
//
//    @Test
//    void verifyEmail_UserNotFound() {
//        String token = "valid-token";
//        String email = "test@example.com";
//
//        VerificationTokenEntity tokenEntity = new VerificationTokenEntity();
//        tokenEntity.setToken(token);
//        tokenEntity.setEmail(email);
//        tokenEntity.setExpirationDate(LocalDateTime.now().plusHours(1));
//
//        when(tokenRepository.findByToken(token)).thenReturn(Optional.of(tokenEntity));
//        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
//
//        String result = verificationService.verifyEmail(token);
//
//        assertEquals("User not found.", result);
//    }
//
//    @Test
//    void verifyToken_Success() {
//        String token = "valid-token";
//        VerificationTokenEntity tokenEntity = new VerificationTokenEntity();
//        tokenEntity.setToken(token);
//        tokenEntity.setExpirationDate(LocalDateTime.now().plusHours(1));
//
//        when(tokenRepository.findByToken(token)).thenReturn(Optional.of(tokenEntity));
//
//        boolean isValid = verificationService.verifyToken(token);
//
//        assertEquals(true, isValid);
//    }
//
//    @Test
//    void verifyToken_TokenExpired() {
//        String token = "expired-token";
//        VerificationTokenEntity tokenEntity = new VerificationTokenEntity();
//        tokenEntity.setToken(token);
//        tokenEntity.setExpirationDate(LocalDateTime.now().minusHours(1));
//
//        when(tokenRepository.findByToken(token)).thenReturn(Optional.of(tokenEntity));
//
//        boolean isValid = verificationService.verifyToken(token);
//
//        assertEquals(false, isValid);
//    }
//}
