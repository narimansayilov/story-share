package com.storyshare.controller;

import com.storyshare.dto.criteria.TagCriteriaRequest;
import com.storyshare.dto.request.TagRequest;
import com.storyshare.dto.response.TagResponse;
import com.storyshare.entity.Translation;
import com.storyshare.service.TagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TagControllerTest {

    @Mock
    private TagService tagService;

    @InjectMocks
    private TagController tagController;

    private UUID tagId;
    private TagRequest tagRequest;
    private TagResponse tagResponse;
    private TagCriteriaRequest criteriaRequest;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        tagId = UUID.randomUUID();
        tagRequest = new TagRequest();
        tagRequest.setName("#SampleTag");
        tagRequest.setTranslations(List.of(new Translation("en", "SampleTag")));

        tagResponse = new TagResponse();
        tagResponse.setId(tagId);
        tagResponse.setName("#SampleTag");
        tagResponse.setStoryCount(5);
        tagResponse.setTranslations(List.of(new Translation("en", "SampleTag")));

        criteriaRequest = new TagCriteriaRequest();
        criteriaRequest.setName("#SampleTag");

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void testAddTag() {
        tagController.addTag(tagRequest);

        verify(tagService).addTag(any(TagRequest.class));
    }

    @Test
    void testGetTag() {
        when(tagService.getTag(any(UUID.class))).thenReturn(tagResponse);

        TagResponse result = tagController.getTag(tagId);

        assertEquals(tagResponse, result);
        assertEquals(tagId, result.getId());
        assertEquals("#SampleTag", result.getName());
        assertNotNull(result.getTranslations());
    }

    @Test
    void testGetTags() {
        when(tagService.getAllTags(any(Pageable.class), any(TagCriteriaRequest.class))).thenReturn(List.of(tagResponse));

        List<TagResponse> result = tagController.getTags(pageable, criteriaRequest);

        assertEquals(1, result.size());
        assertEquals(tagResponse, result.get(0));
        verify(tagService).getAllTags(pageable, criteriaRequest);
    }

    @Test
    void testUpdateTag() {
        when(tagService.updateTag(any(UUID.class), any(TagRequest.class))).thenReturn(tagResponse);

        TagResponse result = tagController.updateTag(tagId, tagRequest);

        assertEquals(tagResponse, result);
        assertEquals(tagId, result.getId());
        assertEquals("#SampleTag", result.getName());
    }

    @Test
    void testDeleteTag() {
        tagController.deleteTag(tagId);

        verify(tagService).deleteTag(any(UUID.class));
    }
}