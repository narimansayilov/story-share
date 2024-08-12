package com.storyshare.mapper;

import com.storyshare.dto.request.StoryImageRequest;
import com.storyshare.dto.response.StoryImageResponse;
import com.storyshare.entity.StoryImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StoryImageMapper {
    StoryImageMapper INSTANCE = Mappers.getMapper(StoryImageMapper.class);

    @Mapping(source = "storyId", target = "story.id")
    StoryImageEntity requestToEntity(StoryImageRequest request);

    List<StoryImageResponse> entitiesToResponses(List<StoryImageEntity> entities);
}
