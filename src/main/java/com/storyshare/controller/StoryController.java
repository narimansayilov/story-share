package com.storyshare.controller;

import com.storyshare.dto.criteria.StoryCriteriaRequest;
import com.storyshare.dto.request.StoryRequest;
import com.storyshare.dto.response.StoryResponse;
import com.storyshare.service.StoryService;
import com.storyshare.util.annotation.ValidImages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    public void addStory(@RequestPart("request") @Valid StoryRequest request,
                         @RequestPart("images") @ValidImages List<MultipartFile> images) {
        storyService.addStory(request, images);
    }

    @GetMapping("/{id}")
    public StoryResponse getStoryById(@PathVariable UUID id) {
        return storyService.getStory(id);
    }

    @GetMapping("/all")
    private List<StoryResponse> getAllStories(Pageable pageable, StoryCriteriaRequest criteriaRequest) {
        return storyService.getAllStories(pageable, criteriaRequest);
    }

    @GetMapping("/my")
    public List<StoryResponse> getMyStories(Pageable pageable, StoryCriteriaRequest criteriaRequest) {
        return storyService.getMyStories(pageable, criteriaRequest);
    }

    @PutMapping("/{id}")
    public StoryResponse updateStory(@PathVariable UUID id,
                                     @RequestPart("request") @Valid StoryRequest request,
                                     @RequestPart("images") @ValidImages List<MultipartFile> images) {
        return storyService.updateStory(id, request, images);
    }

    @PatchMapping("/{id}/activate")
    public void activateStory(@PathVariable UUID id) {
        storyService.activateStory(id);
    }

    @PatchMapping("/{id}/deactivate")
    public void deactivateStory(@PathVariable UUID id) {
        storyService.deactivateStory(id);
    }
}