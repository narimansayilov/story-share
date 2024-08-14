package com.storyshare.controller;


import com.storyshare.dto.request.ReviewActionRequest;
import com.storyshare.dto.response.ReviewActionResponse;
import com.storyshare.entity.ReviewActionEntity;
import com.storyshare.entity.UserEntity;
import com.storyshare.exception.NotFoundException;
import com.storyshare.mapper.ReviewActionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.storyshare.service.ReviewActionService;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/review/action")
@RequiredArgsConstructor
public class ReviewActionController {
    private final ReviewActionService reviewActionService;

    @PostMapping("/add")
    public void addReviewAction(@RequestBody ReviewActionRequest request) {
        reviewActionService.addReviewAction(request);
    }

    @GetMapping("/user/actions")
    public List<ReviewActionResponse> findAllByUsername(){
        return reviewActionService.findAllByUsername();
    }

    @GetMapping("/{reviewId}")
    public List<ReviewActionResponse> findAllByReviewID(@PathVariable UUID reviewId){
        return reviewActionService.findAllByReviewID(reviewId);
    }
}    