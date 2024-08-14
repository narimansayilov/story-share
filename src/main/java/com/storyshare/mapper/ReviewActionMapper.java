package com.storyshare.mapper;

import com.storyshare.dto.request.ReviewActionRequest;
import com.storyshare.dto.response.ReviewActionResponse;
import com.storyshare.entity.ReviewActionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReviewActionMapper {
    ReviewActionMapper INSTANCE = Mappers.getMapper(ReviewActionMapper.class);

    @Mapping(source = "reviewId", target = "review.id")
    ReviewActionEntity mapRequestToEntity(ReviewActionRequest Request);

    @Mapping(source = "review.id", target = "reviewId")
    @Mapping(source = "user.id", target = "userId")
    ReviewActionResponse mapEntityToResponse(ReviewActionEntity entities);
}
