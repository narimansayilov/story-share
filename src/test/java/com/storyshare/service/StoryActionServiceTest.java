package com.storyshare.service;

import com.storyshare.dto.response.StoryActionResponse;
import com.storyshare.entity.StoryActionEntity;
import com.storyshare.entity.StoryEntity;
import com.storyshare.entity.UserEntity;
import com.storyshare.enums.StoryActionType;
import com.storyshare.repository.StoryActionRepository;
import com.storyshare.repository.StoryRepository;
import com.storyshare.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class StoryActionServiceTest {

    @Mock
    private StoryActionRepository storyActionRepository;

    @Mock
    private StoryRepository storyRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StoryActionService storyActionService;

    @Test
    void testGetStoryActionByType() {
        StoryActionType type = StoryActionType.LIKE;
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        StoryActionEntity actionEntity = new StoryActionEntity();
        List<StoryActionEntity> actions = List.of(actionEntity);

        Mockito.when(storyActionRepository.findByType(type, sort)).thenReturn(Optional.of(actions));

        List<StoryActionResponse> result = storyActionService.getStoryActionByType(type.name());

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testCreateStoryByType() {
        UUID storyId = UUID.randomUUID();
        StoryActionType type = StoryActionType.LIKE;
        StoryEntity story = new StoryEntity();
        UserEntity user = new UserEntity();

        Mockito.when(storyRepository.findById(storyId)).thenReturn(Optional.of(story));
        Mockito.when(userService.getCurrentUsername()).thenReturn("testUser");
        Mockito.when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        Mockito.when(storyActionRepository.save(Mockito.any(StoryActionEntity.class)))
                .thenAnswer(invocation -> {
                    StoryActionEntity savedEntity = invocation.getArgument(0);
                    savedEntity.setId(UUID.randomUUID()); // Yeni UUID təyin edilir
                    return savedEntity;
                });

        UUID actionId = storyActionService.createStoryByType(storyId, type.name());

        assertNotNull(actionId);
        Mockito.verify(storyActionRepository, Mockito.times(1)).save(Mockito.any(StoryActionEntity.class));
    }


    @Test
    void testDeleteStoryActionById() {
        UUID storyActionId = UUID.randomUUID();
        Mockito.when(storyActionRepository.existsById(storyActionId)).thenReturn(true);

        storyActionService.deleteStoryActionById(storyActionId);

        Mockito.verify(storyActionRepository, Mockito.times(1)).deleteById(storyActionId);
    }
}
