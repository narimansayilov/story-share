package com.storyshare.controller;

import com.storyshare.dto.request.ReviewActionRequest;
import com.storyshare.dto.response.ReviewActionResponse;
import com.storyshare.enums.ReviewActionType;
import com.storyshare.service.ReviewActionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

class ReviewActionControllerTest {

    @Mock
    private ReviewActionService reviewActionService;

    @InjectMocks
    private ReviewActionController reviewActionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddReviewAction() {
        // Arrange
        ReviewActionRequest mockRequest = new ReviewActionRequest();
        mockRequest.setType(ReviewActionType.LIKE);
        mockRequest.setReviewId(UUID.randomUUID());

        // Act
        reviewActionController.addReviewAction(mockRequest);

        // Assert
        verify(reviewActionService).addReviewAction(any(ReviewActionRequest.class));
    }

    @Test
    void testFindAllByUsername() {
        // Arrange
        ReviewActionResponse mockResponse = new ReviewActionResponse();
        mockResponse.setId(UUID.randomUUID());
        mockResponse.setType(ReviewActionType.LIKE);
        mockResponse.setReviewId(UUID.randomUUID());
        mockResponse.setUserId(UUID.randomUUID());

        when(reviewActionService.findAllByUsername()).thenReturn(List.of(mockResponse));

        // Act
        List<ReviewActionResponse> result = reviewActionController.findAllByUsername();

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(mockResponse, result.get(0));
    }

    @Test
    void testFindAllByReviewID() {
        // Arrange
        UUID reviewId = UUID.randomUUID();
        ReviewActionResponse mockResponse = new ReviewActionResponse();
        mockResponse.setId(UUID.randomUUID());
        mockResponse.setType(ReviewActionType.DISLIKE);
        mockResponse.setReviewId(reviewId);
        mockResponse.setUserId(UUID.randomUUID());

        when(reviewActionService.findAllByReviewID(any(UUID.class))).thenReturn(List.of(mockResponse));

        // Act
        List<ReviewActionResponse> result = reviewActionController.findAllByReviewID(reviewId);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(mockResponse, result.get(0));
    }

    @Test
    void testDeleteReviewAction() {
        // Arrange
        UUID reviewId = UUID.randomUUID();

        // Act
        reviewActionController.deleteReviewAction(reviewId);

        // Assert
        verify(reviewActionService).deleteReviewAction(any(UUID.class));
    }
}