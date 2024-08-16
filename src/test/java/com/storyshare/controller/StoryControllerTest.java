package com.storyshare.controller;

import com.storyshare.dto.criteria.StoryCriteriaRequest;
import com.storyshare.dto.request.StoryRequest;
import com.storyshare.dto.response.StoryResponse;
import com.storyshare.service.StoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

class StoryControllerTest {
    @Mock
    StoryService storyService;

    @InjectMocks
    StoryController storyController;

    private UUID storyId;
    private StoryRequest storyRequest;
    private StoryResponse storyResponse;
    private List<MultipartFile> images;
    private Pageable pageable;
    private StoryCriteriaRequest criteriaRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        storyId = UUID.randomUUID();
        storyRequest = new StoryRequest(); // initialize with necessary fields
        storyResponse = new StoryResponse(); // initialize with necessary fields
        images = List.of(mock(MultipartFile.class)); // mock MultipartFile
        pageable = PageRequest.of(0, 10);
        criteriaRequest = new StoryCriteriaRequest(); // initialize with necessary fields
    }

    @Test
    void testAddStory() {
        storyController.addStory(storyRequest, images);
        verify(storyService).addStory(any(StoryRequest.class), anyList());
    }

    @Test
    void testGetStoryById() {
        when(storyService.getStory(any(UUID.class))).thenReturn(storyResponse);

        StoryResponse result = storyController.getStoryById(storyId);

        Assertions.assertEquals(storyResponse, result);
        verify(storyService).getStory(storyId);
    }

    @Test
    void testGetMyStories() {
        when(storyService.getMyStories(any(Pageable.class), any(StoryCriteriaRequest.class)))
                .thenReturn(List.of(storyResponse));

        List<StoryResponse> result = storyController.getMyStories(pageable, criteriaRequest);

        Assertions.assertEquals(List.of(storyResponse), result);
        verify(storyService).getMyStories(pageable, criteriaRequest);
    }

    @Test
    void testUpdateStory() {
        when(storyService.updateStory(any(UUID.class), any(StoryRequest.class), anyList()))
                .thenReturn(storyResponse);

        StoryResponse result = storyController.updateStory(storyId, storyRequest, images);

        Assertions.assertEquals(storyResponse, result);
        verify(storyService).updateStory(storyId, storyRequest, images);
    }

    @Test
    void testActivateStory() {
        storyController.activateStory(storyId);
        verify(storyService).activateStory(storyId);
    }

    @Test
    void testDeactivateStory() {
        storyController.deactivateStory(storyId);
        verify(storyService).deactivateStory(storyId);
    }
}