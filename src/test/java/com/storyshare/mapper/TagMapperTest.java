package com.storyshare.mapper;

import com.storyshare.dto.request.TagRequest;
import com.storyshare.dto.response.TagResponse;
import com.storyshare.entity.TagEntity;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TagMapperTest {

    private EnhancedRandom random;

    @BeforeEach
    void setUp() {
        random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();
    }

    @Test
    void testRequestToEntity() {
        // Arrange
        TagRequest tagRequest = random.nextObject(TagRequest.class);

        // Act
        TagEntity tagEntity = TagMapper.INSTANCE.requestToEntity(tagRequest);

        // Assert
        assertEquals(tagRequest.getName(), tagEntity.getName());
        assertEquals(tagRequest.getTranslations(), tagEntity.getTranslations());
    }

    @Test
    void testEntityToResponse() {
        // Arrange
        TagEntity tagEntity = random.nextObject(TagEntity.class);

        // Act
        TagResponse tagResponse = TagMapper.INSTANCE.entityToResponse(tagEntity);

        // Assert
        assertEquals(tagEntity.getId(), tagResponse.getId());
        assertEquals(tagEntity.getName(), tagResponse.getName());
        assertEquals(tagEntity.getStoryCount(), tagResponse.getStoryCount());
        assertEquals(tagEntity.getTranslations(), tagResponse.getTranslations());
    }

    @Test
    void testEntitiesToResponses() {
        // Arrange
        List<TagEntity> tagEntities = List.of(
                random.nextObject(TagEntity.class),
                random.nextObject(TagEntity.class),
                random.nextObject(TagEntity.class)
        );
        Page<TagEntity> page = new PageImpl<>(tagEntities);

        // Act
        List<TagResponse> tagResponses = TagMapper.INSTANCE.entitiesToResponses(page);

        // Assert
        assertEquals(tagEntities.size(), tagResponses.size());

        tagResponses.forEach(tagResponse ->
                assertTrue(tagEntities.stream().anyMatch(tagEntity ->
                        tagEntity.getId().equals(tagResponse.getId()) &&
                                tagEntity.getName().equals(tagResponse.getName()) &&
                                tagEntity.getStoryCount().equals(tagResponse.getStoryCount()) &&
                                tagEntity.getTranslations().equals(tagResponse.getTranslations())
                ))
        );
    }

    @Test
    void testMapRequestToEntity() {
        // Arrange
        TagRequest tagRequest = random.nextObject(TagRequest.class);
        TagEntity tagEntity = new TagEntity();

        // Act
        TagMapper.INSTANCE.mapRequestToEntity(tagEntity, tagRequest);

        // Assert
        assertEquals(tagRequest.getName(), tagEntity.getName());
        assertEquals(tagRequest.getTranslations(), tagEntity.getTranslations());
    }
}
