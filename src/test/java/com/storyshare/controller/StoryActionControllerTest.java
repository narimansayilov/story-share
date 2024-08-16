package com.storyshare.controller;

import com.storyshare.dto.response.StoryActionResponse;
import com.storyshare.enums.StoryActionType;
import com.storyshare.service.StoryActionService;
import com.storyshare.dto.response.StoryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class StoryActionControllerTest {

    @InjectMocks
    private StoryActionController storyActionController;

    @Mock
    private StoryActionService storyActionService;

    private StoryActionResponse storyActionResponse;
    private UUID storyId;
    private UUID storyActionId;
    private String actionType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        storyId = UUID.randomUUID();
        storyActionId = UUID.randomUUID();
        actionType = "LIKE";

        storyActionResponse = new StoryActionResponse();
        storyActionResponse.setId(storyActionId);
        storyActionResponse.setType(StoryActionType.valueOf(actionType));
        storyActionResponse.setCreatedAt(LocalDateTime.now());

        StoryResponse storyResponse = new StoryResponse();
        storyResponse.setId(storyId);
        storyResponse.setTitle("Sample Story Title");
        storyResponse.setDescription("Sample description");
        storyResponse.setViewCount(100);
        storyResponse.setLikeCount(10);
        storyResponse.setDislikeCount(2);
        storyResponse.setCommentCount(5);
        storyResponse.setFavoriteCount(15);

        storyActionResponse.setStory(storyResponse);
    }

    @Test
    void testGetStoryActionByType() {
        when(storyActionService.getStoryActionByType(anyString())).thenReturn(List.of(storyActionResponse));

        ResponseEntity<List<StoryActionResponse>> responseEntity = storyActionController.getStoryActionByType(actionType);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().size());
        StoryActionResponse actualResponse = responseEntity.getBody().get(0);
        assertEquals(actionType, actualResponse.getType().name());
        assertNotNull(actualResponse.getStory());
        assertNotNull(actualResponse.getCreatedAt());
    }

    @Test
    void testCreateStoryByType() {
        when(storyActionService.createStoryByType(any(UUID.class), anyString())).thenReturn(storyActionId);

        ResponseEntity<UUID> responseEntity = storyActionController.createStoryByType(storyId, actionType);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(storyActionId, responseEntity.getBody());
    }

    @Test
    void testDeleteStoryAction() {
        doNothing().when(storyActionService).deleteStoryActionById(any(UUID.class));

        ResponseEntity<String> responseEntity = storyActionController.deleteStoryAction(storyActionId);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("Deleted story action", responseEntity.getBody());
    }
}