package com.storyshare.controller;

import com.storyshare.enums.VerificationType;
import com.storyshare.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VerificationController {
    private final VerificationService verificationService;

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        String message = verificationService.verifyToken(token, VerificationType.EMAIL_VERIFICATION);
        return ResponseEntity.ok(message);
    }
}
