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
@Table(name = "story_images")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StoryImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    Boolean isMain;
    Boolean status;
    String url;

    @ManyToOne
    @JoinColumn(name = "story_id")
    StoryEntity story;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @PrePersist
    protected void autoFill() {
        this.status = true;
    }
}
