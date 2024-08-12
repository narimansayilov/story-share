package com.storyshare.service;

import com.storyshare.dto.response.StoryActionResponse;
import com.storyshare.entity.StoryActionEntity;
import com.storyshare.entity.StoryEntity;
import com.storyshare.entity.UserEntity;
import com.storyshare.enums.StoryActionType;
import com.storyshare.exception.NotFoundException;
import com.storyshare.mapper.StoryActionMapper;
import com.storyshare.repository.StoryActionRepository;
import com.storyshare.repository.StoryRepository;
import com.storyshare.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoryActionService {
    private final StoryActionRepository storyActionRepository;
    private final StoryRepository storyRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public List<StoryActionResponse> getStoryActionByType(String type) {
        StoryActionType actionType;
        try {
            actionType = StoryActionType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid story action type");
        }
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        List<StoryActionEntity> actions = storyActionRepository.findByType(actionType, sort)
                .orElseThrow(() -> new NotFoundException("STORY_NOT_FOUND"));
        return actions.stream()
                .map(StoryActionMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public UUID createStoryByType(UUID storyId, String type) {
        StoryActionType actionType;
        try {
            actionType = StoryActionType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid story action type");
        }
        StoryEntity story = storyRepository.findById(storyId)
                .orElseThrow(() -> new NotFoundException("Story not found"));
        String currentUsername = userService.getCurrentUsername();
        UserEntity user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new NotFoundException("User not found"));
        StoryActionEntity action = createStoryAction(story, user, actionType);
        return action.getId();
    }

    protected StoryActionEntity createStoryAction(StoryEntity story, UserEntity user, StoryActionType actionType) {
        StoryActionEntity action = new StoryActionEntity();
        action.setType(actionType);
        action.setStory(story);
        action.setUser(user);
        storyActionRepository.save(action);
        return action;
    }
    public void deleteStoryActionById(UUID storyActionId) {
        if (!storyActionRepository.existsById(storyActionId)) {
            throw new NotFoundException("ACTION_NOT_FOUND");
        }
        storyActionRepository.deleteById(storyActionId);
    }
}
