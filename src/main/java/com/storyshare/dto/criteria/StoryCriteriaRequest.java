package com.storyshare.dto.criteria;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class StoryCriteriaRequest {
    private String title;
    private String description;
    private UUID cityId;
    private List<UUID> tagIds;
}
