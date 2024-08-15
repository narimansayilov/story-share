package com.storyshare.service;

import com.storyshare.entity.VerificationTokenEntity;
import com.storyshare.enums.VerificationType;
import com.storyshare.exception.NotFoundException;
import com.storyshare.repository.UserRepository;
import com.storyshare.repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationTokenRepository tokenRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;

    @Transactional
    public void generateAndSendToken(String email, VerificationType type) {
        String token = generateToken();
        LocalDateTime expirationDate = LocalDateTime.now().plusHours(1);

        VerificationTokenEntity verificationToken = VerificationTokenEntity.builder()
                .token(token)
                .email(email)
                .expirationDate(expirationDate)
                .type(type)
                .build();

        tokenRepository.save(verificationToken);
        sendEmailByType(email, type, token);
    }

    @Transactional
    public String verifyToken(String token, VerificationType type) {
        return tokenRepository.findByToken(token)
                .filter(verificationToken -> verificationToken.getType() == type)
                .filter(verificationToken -> LocalDateTime.now().isBefore(verificationToken.getExpirationDate()))
                .map(verificationToken -> {
                    if (type == VerificationType.EMAIL_VERIFICATION) {
                        return userRepository.findByEmail(verificationToken.getEmail())
                                .map(user -> {
                                    user.setVerified(true);
                                    userRepository.save(user);
                                    tokenRepository.delete(verificationToken);
                                    return "Email successfully verified!";
                                })
                                .orElse("User not found.");
                    } else {
                        return "Invalid token type.";
                    }
                })
                .orElse("Invalid or expired token.");
    }

    public boolean verifyToken(String token) {
        return tokenRepository.findByToken(token)
                .filter(verificationToken -> LocalDateTime.now().isBefore(verificationToken.getExpirationDate()))
                .isPresent();
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void sendEmailByType(String email,VerificationType type, String token) {
        String emailSubject = "Email Verification";
        String passwordSubject = "Password Reset Request";
        String emailUrl = "http://localhost:8080/verify-email?token=";
        String emailBody = "Please verify your action by clicking the following link:\n";

        String subject = (type == VerificationType.EMAIL_VERIFICATION) ? emailSubject : passwordSubject;
        String body = (type == VerificationType.EMAIL_VERIFICATION) ? emailBody + emailUrl + token : token;

        emailService.sendEmail(email, subject,body);
    }
}