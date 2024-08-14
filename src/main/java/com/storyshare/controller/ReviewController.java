package com.storyshare.controller;


import com.storyshare.dto.request.ReviewRequest;
import com.storyshare.dto.response.ReviewResponse;
import com.storyshare.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/all")
    public List<ReviewResponse> getAllReview(Pageable pageable){
        return reviewService.getAllReview(pageable);
    }

    @GetMapping("/story/{storyId}")
    public List<ReviewResponse> getAllReviewByStoryId(Pageable pageable, @PathVariable UUID storyId){
        return reviewService.getAllReviewByStoryId(pageable, storyId);
    }

    @GetMapping("/{id}")
    public ReviewResponse getReviewById(@PathVariable UUID id){
        return reviewService.getReviewById(id);
    }

    @GetMapping("/reply/{id}")
    public List<ReviewResponse> getReviewReplayById(@PathVariable UUID id) {
        return reviewService.getReviewReplyById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReviewById(@PathVariable UUID id) {
        reviewService.deleteReviewById(id);
    }
}    