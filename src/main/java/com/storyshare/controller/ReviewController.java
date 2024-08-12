package com.storyshare.controller;


import com.storyshare.dto.request.ReviewRequest;
import com.storyshare.dto.response.ReviewResponse;
import com.storyshare.entity.ReviewEntity;
import com.storyshare.entity.StoryEntity;
import com.storyshare.entity.UserEntity;
import com.storyshare.exception.NotFoundException;
import com.storyshare.mapper.ReviewMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.storyshare.service.ReviewService;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/add")
    public void addReview(@RequestBody ReviewRequest request){
        reviewService.addReview(request);
    }

    @GetMapping("/{id}")
    public ReviewResponse getReviewById(@PathVariable UUID id){
        return reviewService.getReviewById(id);
    }

    @GetMapping("/replay/{id}")
    public List<ReviewResponse> getReviewReplayById(@PathVariable UUID id) {
        return reviewService.getReviewReplayById(id);
    }
}    