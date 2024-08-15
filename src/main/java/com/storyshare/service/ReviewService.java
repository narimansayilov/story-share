package com.storyshare.service;

import com.storyshare.dto.request.ReviewRequest;
import com.storyshare.dto.response.ReviewResponse;
import com.storyshare.dto.response.StoryResponse;
import com.storyshare.dto.response.UserResponse;
import com.storyshare.entity.ReviewEntity;
import com.storyshare.entity.StoryEntity;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final StoryRepository storyRepository;

    public void addReview(ReviewRequest request) {
        ReviewEntity entity = ReviewMapper.INSTANCE.mapRequestToEntity(request);
        UserEntity user = userRepository.findByUsername(userService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("USER NOT FOUND WITH USERNAME: " + userService.getCurrentUsername()));
        StoryEntity story = storyRepository.findById(request.getStoryId())
                .orElseThrow(() -> new NotFoundException("STORY NOT FOUND WITH ID: " + request.getStoryId()));

        if (!request.getParentReview()) {
            ReviewEntity parent = reviewRepository.findById(request.getParentId())
                    .orElseThrow(() -> new NotFoundException("REVIEW NOT FOUND WITH ID: " + request.getParentId()));
            entity.setParent(parent);
            parent.setReplyCount(parent.getReplyCount() == null ? 1 : parent.getReplyCount() + 1);
        } else entity.setParent(null);

        entity.setUser(user);
        entity.setStory(story);
        story.setCommentCount(story.getCommentCount() == null ? 1 : story.getCommentCount() + 1);
        entity.setReplyCount(entity.getReplyCount() == null ? 1 : entity.getReplyCount() + 1);
        reviewRepository.save(entity);
    }

    public List<ReviewResponse> getAllReviewByStoryId(Pageable pageable, UUID storyId){
        Page<ReviewEntity> reviews = reviewRepository.findByStoryId(pageable, storyId);
        return ReviewMapper.INSTANCE.mapEntityToResponseList(reviews);
    }

    public ReviewResponse getReviewById(UUID id) {
        ReviewEntity entity = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("REVIEW NOT FOUND"));
        UserResponse user = UserMapper.INSTANCE.entityToResponse(entity.getUser());
        ReviewResponse reviewResponse = ReviewMapper.INSTANCE.mapEntityToResponse(entity);
        reviewResponse.setUser(user);
        reviewResponse.setStoryId(entity.getStory().getId());
        return reviewResponse;
    }

    public List<ReviewResponse> getReviewReplyById(Pageable pageable, UUID id) {
        reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("REVIEW NOT FOUND"));
        return reviewRepository.findByParentId(pageable, id).stream()
                .map(ReviewMapper.INSTANCE::mapEntityToResponse)
                .toList();
    }

    public void deleteReviewById(UUID id) {
        ReviewEntity entity = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("REVIEW NOT FOUND WITH ID: " + id));
        entity.setStatus(false);
        reviewRepository.save(entity);
    }
}
