package com.storyshare.service;

import com.storyshare.entity.UserEntity;
import com.storyshare.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private EmailService emailService;

    @Test
    void sendVerificationEmail_Success() {
        String to = "test@example.com";
        String token = "valid-token";

        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        emailService.sendVerificationEmail(to, token);

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendVerificationEmail_Failure() {
        String to = "test@example.com";
        String token = "valid-token";

        doThrow(new RuntimeException("Mail exception")).when(javaMailSender).send(any(SimpleMailMessage.class));

        assertThrows(RuntimeException.class, () -> emailService.sendVerificationEmail(to, token));

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendCurrentUserEmail_Success() {
        String subject = "Test Subject";
        String text = "Test Email Content";
        String currentUsername = "testUser";
        UserEntity user = new UserEntity();
        user.setEmail("user@example.com");

        when(userService.getCurrentUsername()).thenReturn(currentUsername);
        when(userRepository.findByUsername(currentUsername)).thenReturn(java.util.Optional.of(user));

        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        emailService.sendCurrentUserEmail(subject, text);

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendCurrentUserEmail_UserNotFound() {
        String subject = "Test Subject";
        String text = "Test Email Content";
        String currentUsername = "testUser";

        when(userService.getCurrentUsername()).thenReturn(currentUsername);
        when(userRepository.findByUsername(currentUsername)).thenReturn(java.util.Optional.empty());

        assertThrows(RuntimeException.class, () -> emailService.sendCurrentUserEmail(subject, text));

        verify(javaMailSender, never()).send(any(SimpleMailMessage.class));
    }
}

