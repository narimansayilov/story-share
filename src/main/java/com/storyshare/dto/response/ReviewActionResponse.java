package com.storyshare.dto.response;

import com.storyshare.entity.ReviewEntity;
import com.storyshare.entity.UserEntity;
import com.storyshare.enums.ReviewActionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewActionResponse {
    UUID id;

    ReviewActionType type;

    UUID reviewId;

    UUID userId;
}
