package com.storyshare.controller;

import com.storyshare.dto.criteria.StoryCriteriaRequest;
import com.storyshare.dto.request.StoryRequest;
import com.storyshare.dto.response.*;
import com.storyshare.entity.Translation;
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
    private StoryService storyService;

    @InjectMocks
    private StoryController storyController;

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

        storyRequest = new StoryRequest();
        storyRequest.setTitle("Sample Story Title");
        storyRequest.setDescription("Sample story description for testing.");
        storyRequest.setCityId(UUID.randomUUID());
        storyRequest.setTagIds(List.of(UUID.randomUUID(), UUID.randomUUID()));

        storyResponse = new StoryResponse();
        storyResponse.setId(storyId);
        storyResponse.setTitle("Sample Story Title");
        storyResponse.setDescription("Sample story description for testing.");
        storyResponse.setViewCount(100);
        storyResponse.setLikeCount(10);
        storyResponse.setDislikeCount(2);
        storyResponse.setCommentCount(5);
        storyResponse.setFavoriteCount(15);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(UUID.randomUUID());
        userResponse.setUsername("testuser");
        userResponse.setName("John");
        userResponse.setSurname("Doe");
        userResponse.setEmail("john.doe@example.com");
        userResponse.setStoryCount(1);
        userResponse.setPhotoUrl("http://example.com/photo.jpg");

        storyResponse.setUser(userResponse);

        CityResponse cityResponse = new CityResponse();
        cityResponse.setId(UUID.randomUUID());
        cityResponse.setName("Sample City");
        cityResponse.setParentCity(false);
        cityResponse.setStoryCount(20);
        cityResponse.setParentId(null);
        storyResponse.setCity(cityResponse);

        TagResponse tagResponse = new TagResponse();
        tagResponse.setId(UUID.randomUUID());
        tagResponse.setName("Sample Tag");
        tagResponse.setStoryCount(10);
        tagResponse.setTranslations(List.of(new Translation("en", "Sample Tag")));

        storyResponse.setTags(List.of(tagResponse));

        StoryImageResponse storyImageResponse = new StoryImageResponse();
        storyImageResponse.setId(UUID.randomUUID());
        storyImageResponse.setUrl("http://example.com/image.jpg");
        storyImageResponse.setIsMain(true);

        storyResponse.setImages(List.of(storyImageResponse));

        images = List.of(mock(MultipartFile.class));
        pageable = PageRequest.of(0, 10);
        criteriaRequest = new StoryCriteriaRequest();
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