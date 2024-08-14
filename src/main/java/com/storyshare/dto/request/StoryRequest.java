package com.storyshare.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class StoryRequest {

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 127, message = "Title cannot exceed 255 characters")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "City ID cannot be null")
    private UUID cityId;

    @NotEmpty(message = "Tag IDs list cannot be empty")
    private List<UUID> tagIds;
}
