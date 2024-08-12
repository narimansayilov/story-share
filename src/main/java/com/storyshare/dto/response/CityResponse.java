package com.storyshare.dto.response;

import com.storyshare.entity.Translation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CityResponse {
    private UUID id;
    private String name;
    private Boolean parentCity;
    private Integer storyCount;
    private UUID parentId;
    List<Translation> translations;
}
