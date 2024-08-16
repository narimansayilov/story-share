package com.storyshare.controller;

import com.storyshare.dto.request.ReviewActionRequest;
import com.storyshare.dto.response.ReviewActionResponse;
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
    ReviewActionService reviewActionService;
    @InjectMocks
    ReviewActionController reviewActionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddReviewAction() {
        reviewActionController.addReviewAction(new ReviewActionRequest());
        verify(reviewActionService).addReviewAction(any(ReviewActionRequest.class));
    }

    @Test
    void testFindAllByUsername() {
        when(reviewActionService.findAllByUsername()).thenReturn(List.of(new ReviewActionResponse()));

        List<ReviewActionResponse> result = reviewActionController.findAllByUsername();
        Assertions.assertEquals(List.of(new ReviewActionResponse()), result);
    }

    @Test
    void testFindAllByReviewID() {
        when(reviewActionService.findAllByReviewID(any(UUID.class))).thenReturn(List.of(new ReviewActionResponse()));

        List<ReviewActionResponse> result = reviewActionController.findAllByReviewID(new UUID(0L, 0L));
        Assertions.assertEquals(List.of(new ReviewActionResponse()), result);
    }

    @Test
    void testDeleteReviewAction() {
        reviewActionController.deleteReviewAction(new UUID(0L, 0L));
        verify(reviewActionService).deleteReviewAction(any(UUID.class));
    }
}