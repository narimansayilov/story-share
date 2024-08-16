package com.storyshare.controller;

import com.storyshare.dto.request.ReviewRequest;
import com.storyshare.dto.response.ReviewResponse;
import com.storyshare.service.ReviewService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddReview() {
        ReviewRequest mockReviewRequest = new ReviewRequest();
        mockReviewRequest.setComment("Great story!");
        mockReviewRequest.setParentReview(true);
        mockReviewRequest.setParentId(UUID.randomUUID());
        mockReviewRequest.setStoryId(UUID.randomUUID());

        reviewController.addReview(mockReviewRequest);

        verify(reviewService).addReview(any(ReviewRequest.class));
    }

    @Test
    void testGetAllReviewByStoryId() {
        UUID storyId = UUID.randomUUID();
        ReviewResponse mockReviewResponse = new ReviewResponse();
        mockReviewResponse.setId(UUID.randomUUID());
        mockReviewResponse.setComment("Amazing!");
        mockReviewResponse.setLikeCount(10);
        mockReviewResponse.setDislikeCount(2);
        mockReviewResponse.setReplyCount(3);
        mockReviewResponse.setStoryId(storyId);

        when(reviewService.getAllReviewByStoryId(any(Pageable.class), any(UUID.class)))
                .thenReturn(List.of(mockReviewResponse));

        List<ReviewResponse> result = reviewController.getAllReviewByStoryId(Pageable.unpaged(), storyId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(mockReviewResponse, result.get(0));
    }

    @Test
    void testGetReviewById() {
        UUID reviewId = UUID.randomUUID();
        ReviewResponse mockReviewResponse = new ReviewResponse();
        mockReviewResponse.setId(reviewId);
        mockReviewResponse.setComment("Excellent review!");
        mockReviewResponse.setLikeCount(5);
        mockReviewResponse.setDislikeCount(0);
        mockReviewResponse.setReplyCount(1);

        when(reviewService.getReviewById(any(UUID.class))).thenReturn(mockReviewResponse);

        ReviewResponse result = reviewController.getReviewById(reviewId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockReviewResponse, result);
        Assertions.assertEquals(reviewId, result.getId());
    }

    @Test
    void testGetReviewReplayById() {
        UUID reviewId = UUID.randomUUID();
        ReviewResponse mockReviewResponse = new ReviewResponse();
        mockReviewResponse.setId(UUID.randomUUID());
        mockReviewResponse.setComment("This is a reply to a review.");
        mockReviewResponse.setLikeCount(2);
        mockReviewResponse.setDislikeCount(1);
        mockReviewResponse.setReplyCount(0);

        when(reviewService.getReviewReplyById(any(Pageable.class), any(UUID.class)))
                .thenReturn(List.of(mockReviewResponse));

        List<ReviewResponse> result = reviewController.getReviewReplayById(Pageable.unpaged(), reviewId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(mockReviewResponse, result.get(0));
    }

    @Test
    void testDeleteReviewById() {
        UUID reviewId = UUID.randomUUID();

        reviewController.deleteReviewById(reviewId);

        verify(reviewService).deleteReviewById(any(UUID.class));
    }
}