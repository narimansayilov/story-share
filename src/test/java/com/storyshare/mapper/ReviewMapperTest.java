package com.storyshare.mapper;

import com.storyshare.dto.request.ReviewRequest;
import com.storyshare.dto.response.ReviewResponse;
import com.storyshare.entity.ReviewEntity;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReviewMapperTest {

    private EnhancedRandom random;

    @BeforeEach
    void setUp() {
        random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();
    }

    @Test
    void testMapRequestToEntity() {
        // Arrange
        ReviewRequest reviewRequest = random.nextObject(ReviewRequest.class);

        // Act
        ReviewEntity reviewEntity = ReviewMapper.INSTANCE.mapRequestToEntity(reviewRequest);

        // Assert
        assertNotNull(reviewEntity);
        assertEquals(reviewRequest.getComment(), reviewEntity.getComment());
        assertEquals(reviewRequest.getParentReview(), reviewEntity.getParentReview());
        assertEquals(reviewRequest.getParentId(), reviewEntity.getParent() != null ? reviewEntity.getParent().getId() : null);
        assertEquals(reviewRequest.getStoryId(), reviewEntity.getStory() != null ? reviewEntity.getStory().getId() : null);
    }

    @Test
    void testMapEntityToResponse() {
        // Arrange
        ReviewEntity reviewEntity = random.nextObject(ReviewEntity.class);

        // Act
        ReviewResponse reviewResponse = ReviewMapper.INSTANCE.mapEntityToResponse(reviewEntity);

        // Assert
        assertNotNull(reviewResponse);
        assertEquals(reviewEntity.getId(), reviewResponse.getId());
        assertEquals(reviewEntity.getComment(), reviewResponse.getComment());
        assertEquals(reviewEntity.getLikeCount(), reviewResponse.getLikeCount());
        assertEquals(reviewEntity.getDislikeCount(), reviewResponse.getDislikeCount());
        assertEquals(reviewEntity.getReplyCount(), reviewResponse.getReplyCount());
        assertEquals(reviewEntity.getStory().getId(), reviewResponse.getStoryId());
    }

    @Test
    void testMapEntityToResponseList() {
        // Arrange
        List<ReviewEntity> reviewEntities = List.of(
                random.nextObject(ReviewEntity.class),
                random.nextObject(ReviewEntity.class),
                random.nextObject(ReviewEntity.class)
        );
        Page<ReviewEntity> reviewEntityPage = new PageImpl<>(reviewEntities);

        // Act
        List<ReviewResponse> reviewResponses = ReviewMapper.INSTANCE.mapEntityToResponseList(reviewEntityPage);

        // Assert
        assertEquals(reviewEntities.size(), reviewResponses.size());

        reviewResponses.forEach(response ->
                assertTrue(reviewEntities.stream().anyMatch(entity ->
                        entity.getId().equals(response.getId()) &&
                                entity.getComment().equals(response.getComment()) &&
                                entity.getLikeCount().equals(response.getLikeCount()) &&
                                entity.getDislikeCount().equals(response.getDislikeCount()) &&
                                entity.getReplyCount().equals(response.getReplyCount()) &&
                                entity.getStory().getId().equals(response.getStoryId())
                ))
        );
    }
}
