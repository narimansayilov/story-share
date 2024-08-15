package com.storyshare.service;
import com.storyshare.entity.UserEntity;
import com.storyshare.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;


    @Async
    public void sendVerificationEmail(String to, String token) {
        String verificationUrl = "http://localhost:8080/verify-email?token=" + token;
        String subject = "Email Verification";
        String textMessage = "please verify your email by clicking the following link:\n" + verificationUrl;
        SimpleMailMessage mail = createMail(to, subject, textMessage);
        try {
            javaMailSender.send(mail);
        } catch (MailException e) {
            throw new RuntimeException("Failed to send verification email.", e);
        }
    }

    @Transactional
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = createMail(to, subject, text);
        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            throw new RuntimeException("Error sending email", e);
        }
    }

    private SimpleMailMessage createMail(String to, String subject, String textMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(fromEmail);
        message.setSubject(subject);
        message.setText(textMessage);
        return message;
    }
}