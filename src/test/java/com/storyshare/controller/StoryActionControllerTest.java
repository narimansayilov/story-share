package com.storyshare.controller;

import com.storyshare.dto.response.StoryActionResponse;
import com.storyshare.enums.StoryActionType;
import com.storyshare.service.StoryActionService;
import com.storyshare.dto.response.StoryResponse;
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

    public StoryActionControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStoryActionByType() {
        String type = "LIKE";
        StoryActionResponse response = new StoryActionResponse();
        response.setType(StoryActionType.valueOf(type));
        response.setStory(new StoryResponse());
        response.setCreatedAt(LocalDateTime.now());
        response.setId(UUID.randomUUID());

        when(storyActionService.getStoryActionByType(type)).thenReturn(List.of(response));

        ResponseEntity<List<StoryActionResponse>> responseEntity = storyActionController.getStoryActionByType(type);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(1, responseEntity.getBody().size());
        StoryActionResponse actualResponse = responseEntity.getBody().get(0);
        assertEquals(type, actualResponse.getType().name());
        assertNotNull(actualResponse.getStory()); // Assuming StoryResponse has a non-null field
    }

    @Test
    void testCreateStoryByType() {
        UUID storyId = UUID.randomUUID();
        String type = "LIKE";
        UUID storyActionId = UUID.randomUUID();

        when(storyActionService.createStoryByType(storyId, type)).thenReturn(storyActionId);

        ResponseEntity<UUID> responseEntity = storyActionController.createStoryByType(storyId, type);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(storyActionId, responseEntity.getBody());
    }

    @Test
    void testDeleteStoryAction() {
        UUID storyActionId = UUID.randomUUID();

        doNothing().when(storyActionService).deleteStoryActionById(storyActionId);

        ResponseEntity<String> responseEntity = storyActionController.deleteStoryAction(storyActionId);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("Deleted story action", responseEntity.getBody());
    }
}
