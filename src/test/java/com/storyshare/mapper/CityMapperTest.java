package com.storyshare.mapper;

import com.storyshare.dto.request.CityRequest;
import com.storyshare.dto.response.CityResponse;
import com.storyshare.entity.CityEntity;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CityMapperTest {

    private EnhancedRandom random;

    @BeforeEach
    void setUp() {
        random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();
    }

    @Test
    void testRequestToEntity() {
        // Arrange
        CityRequest cityRequest = random.nextObject(CityRequest.class);
        cityRequest.setParentId(UUID.randomUUID());

        // Act
        CityEntity cityEntity = CityMapper.INSTANCE.requestToEntity(cityRequest);

        // Assert
        assertEquals(cityRequest.getName(), cityEntity.getName());
        assertEquals(cityRequest.getParentCity(), cityEntity.getParentCity());
        assertNotNull(cityEntity.getParent());
        assertEquals(cityRequest.getParentId(), cityEntity.getParent().getId());
        assertEquals(cityRequest.getTranslations(), cityEntity.getTranslations());
    }

    @Test
    void testEntityToResponse() {
        // Arrange
        CityEntity cityEntity = random.nextObject(CityEntity.class);

        // Act
        CityResponse cityResponse = CityMapper.INSTANCE.entityToResponse(cityEntity);

        // Assert
        assertEquals(cityEntity.getId(), cityResponse.getId());
        assertEquals(cityEntity.getName(), cityResponse.getName());
        assertEquals(cityEntity.getParentCity(), cityResponse.getParentCity());
        assertEquals(cityEntity.getStoryCount(), cityResponse.getStoryCount());
        assertEquals(cityEntity.getTranslations(), cityResponse.getTranslations());

        if (cityEntity.getParent() != null) {
            assertEquals(cityEntity.getParent().getId(), cityResponse.getParentId());
        } else {
            assertNull(cityResponse.getParentId());
        }
    }

    @Test
    void testEntitiesToResponses() {
        // Arrange
        List<CityEntity> cityEntities = List.of(
                random.nextObject(CityEntity.class),
                random.nextObject(CityEntity.class),
                random.nextObject(CityEntity.class)
        );
        Page<CityEntity> cityEntityPage = new PageImpl<>(cityEntities);

        // Act
        List<CityResponse> cityResponses = CityMapper.INSTANCE.entitiesToResponses(cityEntityPage);

        // Assert
        assertEquals(cityEntities.size(), cityResponses.size());

        cityResponses.forEach(cityResponse ->
                assertTrue(cityEntities.stream().anyMatch(cityEntity ->
                        cityEntity.getId().equals(cityResponse.getId()) &&
                                cityEntity.getName().equals(cityResponse.getName()) &&
                                cityEntity.getParentCity().equals(cityResponse.getParentCity()) &&
                                cityEntity.getStoryCount().equals(cityResponse.getStoryCount()) &&
                                cityEntity.getTranslations().equals(cityResponse.getTranslations()) &&
                                (cityEntity.getParent() != null ? cityEntity.getParent().getId().equals(cityResponse.getParentId()) : cityResponse.getParentId() == null)
                ))
        );
    }

    @Test
    void testMapRequestToEntity() {
        // Arrange
        CityRequest cityRequest = random.nextObject(CityRequest.class);
        CityEntity cityEntity = new CityEntity();

        // Act
        CityMapper.INSTANCE.mapRequestToEntity(cityEntity, cityRequest);

        // Assert
        assertEquals(cityRequest.getName(), cityEntity.getName());
        assertEquals(cityRequest.getParentCity(), cityEntity.getParentCity());
        assertEquals(cityRequest.getTranslations(), cityEntity.getTranslations());
        if (cityRequest.getParentId() != null) {
            assertNotNull(cityEntity.getParent());
            assertEquals(cityRequest.getParentId(), cityEntity.getParent().getId());
        } else {
            assertNull(cityEntity.getParent());
        }
    }
}