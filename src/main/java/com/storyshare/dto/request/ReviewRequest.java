package com.storyshare.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewRequest {
    @NotBlank
    String comment;

    @NotBlank
    Boolean parentReview;

    UUID parentId;

    @NotBlank
    UUID userId;

    @NotBlank
    UUID storyId;
}
