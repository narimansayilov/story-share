package com.storyshare.entity;

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
@Table(name = "comments")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String comment;
    Boolean parentReview;
    Integer likeCount;
    Integer dislikeCount;
    Integer replyCount;
    Boolean status;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    ReviewEntity parent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @ManyToOne
    @JoinColumn(name = "story_id")
    StoryEntity story;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    protected void autoFill() {
        this.status = true;
        this.likeCount = 0;
        this.dislikeCount = 0;
    }
}