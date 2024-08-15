package com.storyshare.mapper;

import com.storyshare.dto.request.RoleRequest;
import com.storyshare.dto.response.RoleResponse;
import com.storyshare.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleEntity requestToEntity(RoleRequest roleRequest);

    RoleResponse entityToResponse(RoleEntity roleEntity);

    List<RoleResponse> entitiesToResponses(List<RoleEntity> entities);

    void mapRequestToEntity(@MappingTarget RoleEntity roleEntity, RoleRequest roleRequest);
}
