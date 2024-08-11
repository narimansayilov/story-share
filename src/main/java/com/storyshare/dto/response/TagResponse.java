package com.storyshare.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TagResponse {
    private UUID id;
    private String name;
    private Integer storyCount;
}
