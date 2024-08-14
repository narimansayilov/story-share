package com.storyshare.service;

import com.storyshare.entity.UserEntity;
import com.storyshare.entity.VerificationTokenEntity;
import com.storyshare.repository.UserRepository;
import com.storyshare.repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationTokenRepository tokenRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;

    @Transactional
    public void generateAndSendVerificationToken(String email) {
        String token = generateToken();
        LocalDateTime expirationDate = LocalDateTime.now().plusHours(1);

        VerificationTokenEntity verificationToken = new VerificationTokenEntity();
        verificationToken.setToken(token);
        verificationToken.setEmail(email);
        verificationToken.setExpirationDate(expirationDate);

        try {
            tokenRepository.save(verificationToken);
        } catch (Exception e) {
            throw new RuntimeException("Token could not be saved", e);
        }

        try {
            emailService.sendVerificationEmail(email, token);
        } catch (MailException e) {
            throw new RuntimeException("Failed to send verification email.", e);
        }
    }

    @Transactional
    public String verifyEmail(String token) {
        return tokenRepository.findByToken(token)
                .filter(verificationToken -> LocalDateTime.now().isBefore(verificationToken.getExpirationDate()))
                .map(verificationToken -> {
                    return userRepository.findByEmail(verificationToken.getEmail())
                            .map(user -> {
//                                user.setEmailVerified(true);
                                userRepository.save(user);
                                tokenRepository.delete(verificationToken);
                                return "Email successfully verified!";
                            })
                            .orElse("User not found.");
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
}
