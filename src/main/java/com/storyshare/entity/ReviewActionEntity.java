package com.storyshare.entity;

import com.storyshare.enums.ReviewActionType;
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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviw_actions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewActionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Enumerated(EnumType.STRING)
    ReviewActionType type;

    @ManyToOne
    @JoinColumn(name = "review_id")
    ReviewEntity review;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
