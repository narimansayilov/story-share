package com.storyshare.mapper;

import com.storyshare.dto.request.TagRequest;
import com.storyshare.dto.response.TagResponse;
import com.storyshare.entity.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    TagEntity requestToEntity(TagRequest tagRequest);

    TagResponse entityToResponse(TagEntity tagEntity);

    List<TagResponse> entitiesToResponses(Page<TagEntity> tagEntityPage);

    void mapRequestToEntity(@MappingTarget TagEntity tagEntity, TagRequest tagRequest);
}
