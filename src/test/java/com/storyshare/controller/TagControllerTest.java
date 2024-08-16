package com.storyshare.controller;

import com.storyshare.dto.request.TagRequest;
import com.storyshare.dto.response.TagResponse;
import com.storyshare.service.TagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.Mockito.*;

class TagControllerTest {
    @Mock
    TagService tagService;
    @InjectMocks
    TagController tagController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTag() {
        tagController.addTag(new TagRequest());
        verify(tagService).addTag(any(TagRequest.class));
    }

    @Test
    void testGetTag() {
        when(tagService.getTag(any(UUID.class))).thenReturn(new TagResponse());

        TagResponse result = tagController.getTag(new UUID(0L, 0L));
        Assertions.assertEquals(new TagResponse(), result);
    }

    @Test
    void testUpdateTag() {
        when(tagService.updateTag(any(UUID.class), any(TagRequest.class))).thenReturn(new TagResponse());

        TagResponse result = tagController.updateTag(new UUID(0L, 0L), new TagRequest());
        Assertions.assertEquals(new TagResponse(), result);
    }

    @Test
    void testDeleteTag() {
        tagController.deleteTag(new UUID(0L, 0L));
        verify(tagService).deleteTag(any(UUID.class));
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme