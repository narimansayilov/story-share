package com.storyshare.mapper;

import com.storyshare.dto.request.StoryRequest;
import com.storyshare.dto.response.StoryImageResponse;
import com.storyshare.dto.response.StoryResponse;
import com.storyshare.entity.StoryEntity;
import com.storyshare.entity.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StoryMapper {
    StoryMapper INSTANCE = Mappers.getMapper(StoryMapper.class);

    @Mapping(target = "city.id", source = "request.cityId")
    StoryEntity requestToEntity(StoryRequest request, List<TagEntity> tags);

    StoryResponse entityToResponse(StoryEntity entity, List<StoryImageResponse> images);

    @Mapping(target = "city.id", source = "request.cityId")
    void mapRequestToEntity(@MappingTarget StoryEntity storyEntity, StoryRequest request, List<TagEntity> tags);
}
