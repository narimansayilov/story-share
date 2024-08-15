package com.storyshare.entity;

import com.storyshare.enums.VerificationType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "verification_tokens")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerificationTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String token;
    String email;
    LocalDateTime expirationDate;

    @Enumerated(EnumType.STRING)
    VerificationType type;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;
}
