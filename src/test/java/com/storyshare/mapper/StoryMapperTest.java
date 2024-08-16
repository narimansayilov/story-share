package com.storyshare.mapper;

import com.storyshare.dto.request.StoryRequest;
import com.storyshare.dto.response.StoryImageResponse;
import com.storyshare.dto.response.StoryResponse;
import com.storyshare.entity.StoryEntity;
import com.storyshare.entity.TagEntity;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StoryMapperTest {

    private EnhancedRandom random;

    @BeforeEach
    void setUp() {
        random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();
    }

    @Test
    void testRequestToEntity() {
        // Arrange
        StoryRequest storyRequest = random.nextObject(StoryRequest.class);
        List<TagEntity> tagEntities = random.objects(TagEntity.class, 3).collect(Collectors.toList());

        // Act
        StoryEntity storyEntity = StoryMapper.INSTANCE.requestToEntity(storyRequest, tagEntities);

        // Assert
        assertEquals(storyRequest.getTitle(), storyEntity.getTitle());
        assertEquals(storyRequest.getDescription(), storyEntity.getDescription());
        assertEquals(storyRequest.getCityId(), storyEntity.getCity().getId());
        assertTrue(storyEntity.getTags().containsAll(tagEntities));
    }

    @Test
    void testEntityToResponse() {
        // Arrange
        StoryEntity storyEntity = random.nextObject(StoryEntity.class);
        List<StoryImageResponse> images = random.objects(StoryImageResponse.class, 3).collect(Collectors.toList());

        // Act
        StoryResponse storyResponse = StoryMapper.INSTANCE.entityToResponse(storyEntity, images);

        // Assert
        assertEquals(storyEntity.getId(), storyResponse.getId());
        assertEquals(storyEntity.getTitle(), storyResponse.getTitle());
        assertEquals(storyEntity.getDescription(), storyResponse.getDescription());
        assertEquals(storyEntity.getViewCount(), storyResponse.getViewCount());
        assertEquals(storyEntity.getLikeCount(), storyResponse.getLikeCount());
        assertEquals(storyEntity.getDislikeCount(), storyResponse.getDislikeCount());
        assertEquals(storyEntity.getCommentCount(), storyResponse.getCommentCount());
        assertEquals(storyEntity.getFavoriteCount(), storyResponse.getFavoriteCount());
        assertEquals(storyEntity.getUser().getId(), storyResponse.getUser().getId());
        assertEquals(storyEntity.getCity().getId(), storyResponse.getCity().getId());
        assertTrue(storyResponse.getTags().stream().allMatch(tagResponse ->
                storyEntity.getTags().stream().anyMatch(tagEntity ->
                        tagEntity.getId().equals(tagResponse.getId())
                )
        ));
        assertEquals(images.size(), storyResponse.getImages().size());
    }

    @Test
    void testMapRequestToEntity() {
        // Arrange
        StoryRequest storyRequest = random.nextObject(StoryRequest.class);
        StoryEntity storyEntity = new StoryEntity();
        List<TagEntity> tags = random.objects(TagEntity.class, 3).toList();

        // Act
        StoryMapper.INSTANCE.mapRequestToEntity(storyEntity, storyRequest, tags);

        // Assert
        assertEquals(storyRequest.getTitle(), storyEntity.getTitle());
        assertEquals(storyRequest.getDescription(), storyEntity.getDescription());
        assertEquals(storyRequest.getCityId(), storyEntity.getCity().getId());
        assertEquals(tags.size(), storyEntity.getTags().size());
        assertTrue(storyEntity.getTags().containsAll(tags));
    }
}
