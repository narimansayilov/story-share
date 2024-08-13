package com.storyshare.controller;

import com.storyshare.dto.response.StoryActionResponse;
import com.storyshare.service.StoryActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/storyActions")
@RequiredArgsConstructor
public class StoryActionController {
    private final StoryActionService storyActionService;

    @GetMapping("/{type}")
    public ResponseEntity<List<StoryActionResponse>> getStoryActionByType(@PathVariable String type) {
        List<StoryActionResponse> storyAction = storyActionService.getStoryActionByType(type);
        return ResponseEntity.ok(storyAction);
    }

    @PostMapping("/{storyId}/{type}")
    public ResponseEntity<UUID> createStoryByType(@PathVariable UUID storyId, @PathVariable String type) {
        UUID storyActionId = storyActionService.createStoryByType(storyId, type);
        return ResponseEntity.ok(storyActionId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStoryAction(@PathVariable UUID id) {
        storyActionService.deleteStoryActionById(id);
        return ResponseEntity.ok("Deleted story action");
    }
}