package com.storyshare.mapper;

import com.storyshare.dto.request.ReviewRequest;
import com.storyshare.dto.response.ReviewResponse;
import com.storyshare.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(source = "parentId", target = "parent.id")
    @Mapping(source = "storyId", target = "story.id")
    ReviewEntity mapRequestToEntity(ReviewRequest reviewRequest);

    @Mapping(source = "story.id", target = "storyId")
    ReviewResponse mapEntityToResponse(ReviewEntity reviewEntity);

    List<ReviewResponse> mapEntityToResponseList(Page<ReviewEntity> reviewEntity);
}
