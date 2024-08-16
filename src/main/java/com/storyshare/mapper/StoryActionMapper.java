package com.storyshare.mapper;

import com.storyshare.dto.response.StoryActionResponse;
import com.storyshare.entity.StoryActionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StoryActionMapper {
    StoryActionMapper INSTANCE = Mappers.getMapper(StoryActionMapper.class);

    @Mapping(target = "story.title", source = "story.title")
    StoryActionResponse toResponse(StoryActionEntity entity);
}
