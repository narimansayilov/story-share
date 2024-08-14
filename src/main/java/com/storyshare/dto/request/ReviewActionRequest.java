package com.storyshare.dto.request;

import com.storyshare.enums.ReviewActionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewActionRequest {
    ReviewActionType type;
    UUID reviewId;
}