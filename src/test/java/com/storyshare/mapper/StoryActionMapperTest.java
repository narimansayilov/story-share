package com.storyshare.mapper;

import com.storyshare.dto.response.StoryActionResponse;
import com.storyshare.entity.StoryActionEntity;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StoryActionMapperTest {

    private EnhancedRandom random;

    @BeforeEach
    void setUp() {
        random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();
    }

    @Test
    void testToResponse() {
        // Arrange
        StoryActionEntity entity = random.nextObject(StoryActionEntity.class);

        // Act
        StoryActionResponse response = StoryActionMapper.INSTANCE.toResponse(entity);

        // Assert
        assertNotNull(response);
        assertEquals(entity.getId(), response.getId());
        assertEquals(entity.getType(), response.getType());
        assertNotNull(response.getStory());
        assertEquals(entity.getStory().getTitle(), response.getStory().getTitle());
        assertEquals(entity.getCreatedAt(), response.getCreatedAt());
    }
}
