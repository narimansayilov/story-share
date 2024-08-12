package com.storyshare.controller;

import com.storyshare.dto.response.StoryActionResponse;
import com.storyshare.service.StoryActionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StoryActionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoryActionService storyActionService;

    @Test
    void testGetStoryActionByType() throws Exception {
        String type = "LIKE";
        StoryActionResponse response = new StoryActionResponse();
        Mockito.when(storyActionService.getStoryActionByType(type)).thenReturn(List.of(response));

        mockMvc.perform(get("/storyActions/{type}", type))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testCreateStoryByType() throws Exception {
        UUID storyId = UUID.randomUUID();
        String type = "LIKE";
        Mockito.when(storyActionService.createStoryByType(storyId, type)).thenReturn(storyId);

        mockMvc.perform(post("/storyActions/{storyId}/{type}", storyId, type))
                .andExpect(status().isOk())
                .andExpect(content().string(storyId.toString()));
    }

    @Test
    void testDeleteStoryAction() throws Exception {
        UUID storyActionId = UUID.randomUUID();

        mockMvc.perform(delete("/storyActions/{id}", storyActionId))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted story action"));
    }

}

