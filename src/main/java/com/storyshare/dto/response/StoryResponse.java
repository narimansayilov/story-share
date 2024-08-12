package com.storyshare.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class StoryResponse {
    private UUID id;
    private String title;
    private String description;
    private Integer viewCount;
    private Integer likeCount;
    private Integer dislikeCount;
    private Integer commentCount;
    private Integer favoriteCount;
    private UserResponse user;
    private CityResponse city;
    private List<TagResponse> tags;
    private List<StoryImageResponse> images;
}
