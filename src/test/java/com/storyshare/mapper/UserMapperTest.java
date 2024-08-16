package com.storyshare.mapper;

import com.storyshare.dto.request.UserRegisterRequest;
import com.storyshare.dto.request.UserUpdateRequest;
import com.storyshare.dto.response.UserResponse;
import com.storyshare.entity.UserEntity;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserMapperTest {

    private EnhancedRandom random;

    @BeforeEach
    void setUp() {
        random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();
    }

    @Test
    void testRegisterRequestToEntity() {
        // Arrange
        UserRegisterRequest registerRequest = random.nextObject(UserRegisterRequest.class);

        // Act
        UserEntity userEntity = UserMapper.INSTANCE.registerRequestToEntity(registerRequest);

        // Assert
        assertEquals(registerRequest.getUsername(), userEntity.getUsername());
        assertEquals(registerRequest.getName(), userEntity.getName());
        assertEquals(registerRequest.getSurname(), userEntity.getSurname());
        assertEquals(registerRequest.getEmail(), userEntity.getEmail());
    }

    @Test
    void testEntityToResponse() {
        // Arrange
        UserEntity userEntity = random.nextObject(UserEntity.class);

        // Act
        UserResponse userResponse = UserMapper.INSTANCE.entityToResponse(userEntity);

        // Assert
        assertEquals(userEntity.getId(), userResponse.getId());
        assertEquals(userEntity.getUsername(), userResponse.getUsername());
        assertEquals(userEntity.getName(), userResponse.getName());
        assertEquals(userEntity.getSurname(), userResponse.getSurname());
        assertEquals(userEntity.getEmail(), userResponse.getEmail());
        assertEquals(userEntity.getStoryCount(), userResponse.getStoryCount());
        assertEquals(userEntity.getPhotoUrl(), userResponse.getPhotoUrl());
    }

    @Test
    void testEntitiesToResponses() {
        // Arrange
        List<UserEntity> userEntities = List.of(
                random.nextObject(UserEntity.class),
                random.nextObject(UserEntity.class),
                random.nextObject(UserEntity.class)
        );
        Page<UserEntity> page = new PageImpl<>(userEntities);

        // Act
        List<UserResponse> userResponses = UserMapper.INSTANCE.entitiesToResponses(page);

        // Assert
        assertEquals(userEntities.size(), userResponses.size());

        userResponses.forEach(userResponse ->
                assertTrue(userEntities.stream().anyMatch(userEntity ->
                        userEntity.getId().equals(userResponse.getId()) &&
                                userEntity.getUsername().equals(userResponse.getUsername()) &&
                                userEntity.getName().equals(userResponse.getName()) &&
                                userEntity.getSurname().equals(userResponse.getSurname()) &&
                                userEntity.getEmail().equals(userResponse.getEmail()) &&
                                userEntity.getStoryCount().equals(userResponse.getStoryCount()) &&
                                userEntity.getPhotoUrl().equals(userResponse.getPhotoUrl())
                ))
        );
    }

    @Test
    void testMapRequestToEntity() {
        // Arrange
        UserUpdateRequest updateRequest = random.nextObject(UserUpdateRequest.class);
        UserEntity userEntity = new UserEntity();

        // Act
        UserMapper.INSTANCE.mapRequestToEntity(userEntity, updateRequest);

        // Assert
        assertEquals(updateRequest.getUsername(), userEntity.getUsername());
        assertEquals(updateRequest.getName(), userEntity.getName());
        assertEquals(updateRequest.getSurname(), userEntity.getSurname());
    }
}
