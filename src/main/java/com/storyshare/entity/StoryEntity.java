package com.storyshare.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stories")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String title;
    String description;
    Integer viewCount;
    Integer likeCount;
    Integer dislikeCount;
    Integer commentCount;
    Integer favoriteCount;
    Boolean status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @ManyToOne
    @JoinColumn(name = "city_id")
    CityEntity city;

    @ManyToMany
    @JoinTable(
            name = "stories_tags",
            joinColumns = @JoinColumn(name = "story_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    Set<TagEntity> tags;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    protected void autoFill() {
        this.status = true;
        this.viewCount = 0;
        this.likeCount = 0;
        this.dislikeCount = 0;
        this.commentCount = 0;
        this.favoriteCount = 0;
    }
}
