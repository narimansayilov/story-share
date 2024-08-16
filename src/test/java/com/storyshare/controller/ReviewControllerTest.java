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
    ReviewService reviewService;
    @InjectMocks
    ReviewController reviewController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddReview() {
        reviewController.addReview(new ReviewRequest());
        verify(reviewService).addReview(any(ReviewRequest.class));
    }

    @Test
    void testGetAllReviewByStoryId() {
        when(reviewService.getAllReviewByStoryId(any(Pageable.class), any(UUID.class))).thenReturn(List.of(new ReviewResponse()));

        List<ReviewResponse> result = reviewController.getAllReviewByStoryId(null, new UUID(0L, 0L));
        Assertions.assertEquals(List.of(new ReviewResponse()), result);
    }

    @Test
    void testGetReviewById() {
        when(reviewService.getReviewById(any(UUID.class))).thenReturn(new ReviewResponse());

        ReviewResponse result = reviewController.getReviewById(new UUID(0L, 0L));
        Assertions.assertEquals(new ReviewResponse(), result);
    }

    @Test
    void testGetReviewReplayById() {
        when(reviewService.getReviewReplyById(any(Pageable.class), any(UUID.class))).thenReturn(List.of(new ReviewResponse()));

        List<ReviewResponse> result = reviewController.getReviewReplayById(null, new UUID(0L, 0L));
        Assertions.assertEquals(List.of(new ReviewResponse()), result);
    }

    @Test
    void testDeleteReviewById() {
        reviewController.deleteReviewById(new UUID(0L, 0L));
        verify(reviewService).deleteReviewById(any(UUID.class));
    }
}