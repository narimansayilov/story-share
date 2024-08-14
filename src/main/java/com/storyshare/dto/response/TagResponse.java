package com.storyshare.dto.response;

import com.storyshare.entity.Translation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class TagResponse {
    private UUID id;
    private String name;
    private Integer storyCount;
    private List<Translation> translations;
}
