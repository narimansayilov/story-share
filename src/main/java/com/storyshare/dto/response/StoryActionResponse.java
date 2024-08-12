package com.storyshare.dto.response;

import com.storyshare.enums.StoryActionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class StoryActionResponse {
    private UUID id;
    private StoryActionType type;
    private StoryResponse story;
    private LocalDateTime createdAt;
}
