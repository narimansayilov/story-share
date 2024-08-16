package com.storyshare.mapper;

import com.storyshare.dto.request.ReviewActionRequest;
import com.storyshare.dto.response.ReviewActionResponse;
import com.storyshare.entity.ReviewActionEntity;
import com.storyshare.entity.ReviewEntity;
import com.storyshare.entity.UserEntity;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReviewActionMapperTest {

    private EnhancedRandom random;

    @BeforeEach
    void setUp() {
        random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();
    }

    @Test
    void testMapRequestToEntity() {
        // Arrange
        ReviewActionRequest request = random.nextObject(ReviewActionRequest.class);

        // Act
        ReviewActionEntity entity = ReviewActionMapper.INSTANCE.mapRequestToEntity(request);

        // Assert
        assertNotNull(entity);
        assertEquals(request.getType(), entity.getType());
        assertEquals(request.getReviewId(), entity.getReview() != null ? entity.getReview().getId() : null);
    }

    @Test
    void testMapEntityToResponse() {
        // Arrange
        ReviewActionEntity entity = random.nextObject(ReviewActionEntity.class);
        entity.setReview(new ReviewEntity());
        entity.getReview().setId(UUID.randomUUID());
        entity.setUser(new UserEntity());
        entity.getUser().setId(UUID.randomUUID());

        // Act
        ReviewActionResponse response = ReviewActionMapper.INSTANCE.mapEntityToResponse(entity);

        // Assert
        assertNotNull(response);
        assertEquals(entity.getId(), response.getId());
        assertEquals(entity.getType(), response.getType());
        assertEquals(entity.getReview().getId(), response.getReviewId());
        assertEquals(entity.getUser().getId(), response.getUserId());
    }
}
