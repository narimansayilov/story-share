package com.storyshare.controller;

import com.storyshare.enums.VerificationType;
import com.storyshare.service.VerificationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VerificationControllerTest {

    @Mock
    private VerificationService verificationService;

    @InjectMocks
    private VerificationController verificationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testVerifyEmail() {
        String mockToken = "mockToken123";
        String expectedMessage = "Email verification successful!";

        when(verificationService.verifyToken(eq(mockToken), eq(VerificationType.EMAIL_VERIFICATION)))
                .thenReturn(expectedMessage);

        ResponseEntity<String> response = verificationController.verifyEmail(mockToken);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedMessage, response.getBody());
    }
}