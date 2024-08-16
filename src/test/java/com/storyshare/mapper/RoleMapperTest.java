package com.storyshare.mapper;
import com.storyshare.dto.request.RoleRequest;
import com.storyshare.dto.response.RoleResponse;
import com.storyshare.entity.RoleEntity;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoleMapperTest {

    private EnhancedRandom random;

    @BeforeEach
    void setUp() {
        random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();
    }

    @Test
    void testRequestToEntity() {
        // Arrange
        RoleRequest roleRequest = random.nextObject(RoleRequest.class);

        // Act
        RoleEntity roleEntity = RoleMapper.INSTANCE.requestToEntity(roleRequest);

        // Assert
        assertEquals(roleRequest.getName(), roleEntity.getName());
    }

    @Test
    void testEntityToResponse() {
        // Arrange
        RoleEntity roleEntity = random.nextObject(RoleEntity.class);

        // Act
        RoleResponse roleResponse = RoleMapper.INSTANCE.entityToResponse(roleEntity);

        // Assert
        assertEquals(roleEntity.getId(), roleResponse.getId());
        assertEquals(roleEntity.getName(), roleResponse.getName());
    }

    @Test
    void testEntitiesToResponses() {
        // Arrange
        List<RoleEntity> roleEntities = List.of(
                random.nextObject(RoleEntity.class),
                random.nextObject(RoleEntity.class),
                random.nextObject(RoleEntity.class)
        );

        // Act
        List<RoleResponse> roleResponses = RoleMapper.INSTANCE.entitiesToResponses(roleEntities);

        // Assert
        assertEquals(roleEntities.size(), roleResponses.size());

        roleResponses.forEach(roleResponse ->
                assertTrue(roleEntities.stream().anyMatch(roleEntity ->
                        roleEntity.getId().equals(roleResponse.getId()) &&
                                roleEntity.getName().equals(roleResponse.getName())
                ))
        );
    }

    @Test
    void testMapRequestToEntity() {
        // Arrange
        RoleRequest roleRequest = random.nextObject(RoleRequest.class);
        RoleEntity roleEntity = new RoleEntity();

        // Act
        RoleMapper.INSTANCE.mapRequestToEntity(roleEntity, roleRequest);

        // Assert
        assertEquals(roleRequest.getName(), roleEntity.getName());
    }
}
