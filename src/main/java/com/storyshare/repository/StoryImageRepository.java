package com.storyshare.repository;

import com.storyshare.entity.StoryImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StoryImageRepository extends JpaRepository<StoryImageEntity, UUID> {
    List<StoryImageEntity> findByStoryIdAndStatus(UUID storyId, Boolean status);
}
