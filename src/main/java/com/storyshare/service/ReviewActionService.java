package com.storyshare.service;


import com.storyshare.dto.request.ReviewActionRequest;
import com.storyshare.dto.response.ReviewActionResponse;
import com.storyshare.entity.ReviewActionEntity;
import com.storyshare.entity.ReviewEntity;
import com.storyshare.entity.UserEntity;
import com.storyshare.enums.ReviewActionType;
import com.storyshare.exception.AlreadyExistsException;
import com.storyshare.exception.NotFoundException;
import com.storyshare.mapper.ReviewActionMapper;
import com.storyshare.repository.ReviewRepository;
import com.storyshare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.storyshare.repository.ReviewActionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ReviewActionService {
    private final ReviewActionRepository reviewActionRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public void addReviewAction(ReviewActionRequest request) {
        UserEntity user = userRepository.findByUsername(userService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("USER NOT FOUND WITH USERNAME: " + userService.getCurrentUsername()));
        ReviewActionEntity existingAction = reviewActionRepository.findByUserIdAndReviewId(user.getId(), request.getReviewId());

        if (existingAction != null) {
            if (existingAction.getReview().getParentReview()) {
                throw new AlreadyExistsException("User has already " + existingAction.getType().name().toLowerCase() + "d this story");
            } else {
                throw new AlreadyExistsException("User has already " + existingAction.getType().name().toLowerCase() + "d this review");
            }
        } else {
            ReviewActionEntity entity = ReviewActionMapper.INSTANCE.mapRequestToEntity(request);
            entity.setUser(user);
            increaseCount(request);
            reviewActionRepository.save(entity);
        }
    }

    private void increaseCount(ReviewActionRequest request) {
        ReviewEntity review = reviewRepository.findById(request.getReviewId())
                .orElseThrow(() -> new NotFoundException("REVIEW NOT FOUND WITH ID: " + request.getReviewId()));
        if (request.getType().equals(ReviewActionType.LIKE)) {
            review.setLikeCount(review.getLikeCount() + 1);
        } else {
            review.setDislikeCount(review.getDislikeCount() + 1);
        }
    }

    public List<ReviewActionResponse> findAllByUsername(){
        UserEntity user = userRepository.findByUsername(userService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("USER NOT FOUND WITH USERNAME: " + userService.getCurrentUsername()));
        return reviewActionRepository.findAll().stream()
                .filter(action -> action.getUser().equals(user))
                .map(ReviewActionMapper.INSTANCE::mapEntityToResponse)
                .toList();
    }

    public List<ReviewActionResponse> findAllByReviewID(UUID reviewId){
        List<ReviewActionEntity> entities = reviewActionRepository.findAllByReviewId(reviewId);
        return entities.stream()
                .map(ReviewActionMapper.INSTANCE::mapEntityToResponse)
                .toList();
    }
}