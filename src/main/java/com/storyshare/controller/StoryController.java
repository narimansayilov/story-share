package com.storyshare.controller;

import com.storyshare.dto.request.StoryRequest;
import com.storyshare.dto.response.StoryResponse;
import com.storyshare.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stories")
public class StoryController {
    private final StoryService storyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addStory(@RequestPart("request") StoryRequest request,
                         @RequestPart("images") List<MultipartFile> images) {
        storyService.addStory(request, images);
    }

    @GetMapping("/{id}")
    public StoryResponse getStoryById(@PathVariable UUID id) {
        return storyService.getStory(id);
    }
}
