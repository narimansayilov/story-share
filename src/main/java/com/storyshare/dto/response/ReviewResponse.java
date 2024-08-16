package com.storyshare.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewResponse {
    UUID id;
    String comment;
    Integer likeCount;
    Integer dislikeCount;
    Integer replyCount;
    UserResponse user;
    UUID storyId;
}
