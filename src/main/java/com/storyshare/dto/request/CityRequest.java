package com.storyshare.dto.request;

import com.storyshare.entity.Translation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CityRequest {
    private String name;
    private Boolean parentCity;
    private UUID parentId;
    List<Translation> translations;
}
