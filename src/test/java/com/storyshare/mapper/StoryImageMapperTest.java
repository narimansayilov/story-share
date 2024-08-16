package com.storyshare.mapper;

import com.storyshare.dto.request.StoryImageRequest;
import com.storyshare.dto.response.StoryImageResponse;
import com.storyshare.entity.StoryImageEntity;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StoryImageMapperTest {

    private EnhancedRandom random;

    @BeforeEach
    void setUp() {
        random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();
    }
    @Test
    void testRequestToEntity() {
        // Arrange
        StoryImageRequest request = random.nextObject(StoryImageRequest.class);
        request.setStoryId(UUID.randomUUID());

        // Act
        StoryImageEntity entity = StoryImageMapper.INSTANCE.requestToEntity(request);

        // Assert
        assertNotNull(entity);
        assertEquals(request.getStoryId(), entity.getStory().getId());
        assertEquals(request.getUrl(), entity.getUrl());
        assertEquals(request.getIsMain(), entity.getIsMain());
    }

    @Test
    void testEntitiesToResponses() {
        // Arrange
        List<StoryImageEntity> entities = List.of(
                random.nextObject(StoryImageEntity.class),
                random.nextObject(StoryImageEntity.class),
                random.nextObject(StoryImageEntity.class)
        );

        // Act
        List<StoryImageResponse> responses = StoryImageMapper.INSTANCE.entitiesToResponses(entities);

        // Assert
        assertEquals(entities.size(), responses.size());

        responses.forEach(response ->
                assertTrue(entities.stream().anyMatch(entity ->
                        entity.getId().equals(response.getId()) &&
                                entity.getUrl().equals(response.getUrl()) &&
                                entity.getIsMain().equals(response.getIsMain())
                ))
        );
    }
}
