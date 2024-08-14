package com.storyshare.service;

import com.storyshare.dto.request.ReviewRequest;
import com.storyshare.dto.response.ReviewResponse;
import com.storyshare.dto.response.StoryResponse;
import com.storyshare.dto.response.UserResponse;
import com.storyshare.entity.ReviewEntity;
import com.storyshare.entity.UserEntity;
import com.storyshare.exception.NotFoundException;
import com.storyshare.mapper.ReviewMapper;
import com.storyshare.mapper.StoryMapper;
import com.storyshare.mapper.UserMapper;
import com.storyshare.repository.ReviewRepository;
import com.storyshare.repository.StoryRepository;
import com.storyshare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public void addReview(ReviewRequest request) {

        ReviewEntity entity = ReviewMapper.INSTANCE.mapRequestToEntity(request);

        if (request.getParentReview()) {
            ReviewEntity parent = reviewRepository.findById(request.getParentId())
                    .orElseThrow(() -> new NotFoundException("REVIEW NOT FOUND WITH ID: " + request.getParentId()));
            entity.setParent(parent);
            parent.setReplyCount(parent.getReplyCount() == null ? 1 : parent.getReplyCount() + 1);
        } else entity.setParent(null);

        entity.setReplyCount(entity.getReplyCount() == null ? 1 : entity.getReplyCount() + 1);
        reviewRepository.save(entity);
    }

    public ReviewResponse getReviewById(UUID id) {
        ReviewEntity entity = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("REVIEW NOT FOUND"));
        UserResponse user = UserMapper.INSTANCE.entityToResponse(entity.getUser());
        StoryResponse story = StoryMapper.INSTANCE.entityToResponse(entity.getStory(), null);
        ReviewResponse reviewResponse = ReviewMapper.INSTANCE.mapEntityToResponse(entity);
        reviewResponse.setUser(user);
        reviewResponse.setStory(story);
        return reviewResponse;
    }

    public List<ReviewResponse> getReviewReplayById(UUID id) {
        reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("REVIEW NOT FOUND"));
        return reviewRepository.findByParentId(id).stream()
                .map(ReviewMapper.INSTANCE::mapEntityToResponse)
                .toList();
    }
}
