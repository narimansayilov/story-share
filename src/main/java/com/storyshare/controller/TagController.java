package com.storyshare.controller;

import com.storyshare.dto.criteria.TagCriteriaRequest;
import com.storyshare.dto.request.TagRequest;
import com.storyshare.dto.response.TagResponse;
import com.storyshare.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addTag(@RequestBody TagRequest request) {
        tagService.addTag(request);
    }

    @GetMapping("/{id}")
    public TagResponse getTag(@PathVariable UUID id) {
        return tagService.getTag(id);
    }

    @GetMapping
    private List<TagResponse> getTags(Pageable pageable,
                                      TagCriteriaRequest criteriaRequest) {
        return tagService.getAllTags(pageable, criteriaRequest);
    }

    @PutMapping("/{id}")
    public TagResponse updateTag(@PathVariable UUID id, @RequestBody TagRequest request) {
        return tagService.updateTag(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable UUID id) {
        tagService.deleteTag(id);
    }
}