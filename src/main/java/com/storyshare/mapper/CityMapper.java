package com.storyshare.mapper;

import com.storyshare.dto.request.CityRequest;
import com.storyshare.dto.response.CityResponse;
import com.storyshare.entity.CityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CityMapper {
    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    @Mapping(target = "parent.id", source = "parentId")
    CityEntity requestToEntity(CityRequest request);

    CityResponse entityToResponse(CityEntity entity);

    List<CityResponse> entitiesToResponses(Page<CityEntity> entities);

    void mapRequestToEntity(@MappingTarget CityEntity entity, CityRequest request);
}
