package com.storyshare.repository;

import com.storyshare.entity.StoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoryRepository extends JpaRepository<StoryEntity, UUID> {
}
