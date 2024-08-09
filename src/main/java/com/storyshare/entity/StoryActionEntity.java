package com.storyshare.entity;

import com.storyshare.enums.ReviewActionType;
import com.storyshare.enums.StoryActionType;
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
@Table(name = "story_actions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StoryActionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Enumerated(EnumType.STRING)
    StoryActionType type;

    @ManyToOne
    @JoinColumn(name = "story_id")
    StoryEntity story;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
