package com.storyshare.controller;

import com.storyshare.service.VerificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerificationControllerTest {

    @Mock
    private VerificationService verificationService;

    @InjectMocks
    private VerificationController verificationController;

    @Test
    void verifyEmail_Success() {
        String token = "valid-token";
        String expectedMessage = "Email successfully verified!";

        when(verificationService.verifyEmail(token)).thenReturn(expectedMessage);

        ResponseEntity<String> response = verificationController.verifyEmail(token);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedMessage, response.getBody());

        verify(verificationService, times(1)).verifyEmail(token);
    }

    @Test
    void verifyEmail_Failure() {
        String token = "invalid-token";
        String expectedMessage = "Invalid or expired token.";

        when(verificationService.verifyEmail(token)).thenReturn(expectedMessage);

        ResponseEntity<String> response = verificationController.verifyEmail(token);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedMessage, response.getBody());

        verify(verificationService, times(1)).verifyEmail(token);
    }
}
