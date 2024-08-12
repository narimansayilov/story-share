package com.storyshare.repository;

import com.storyshare.entity.StoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface StoryRepository extends JpaRepository<StoryEntity, UUID>, PagingAndSortingRepository<StoryEntity, UUID>, JpaSpecificationExecutor<StoryEntity> {
}
