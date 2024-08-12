package com.storyshare.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class StoryImageRequest {
    private UUID storyId;
    private String url;
    private Boolean isMain;
}
