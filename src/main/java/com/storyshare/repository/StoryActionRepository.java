package com.storyshare.repository;

import com.storyshare.entity.StoryActionEntity;
import com.storyshare.enums.StoryActionType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoryActionRepository extends JpaRepository<StoryActionEntity, UUID> {
    Optional<List<StoryActionEntity>> findByType(StoryActionType type, Sort sort);
}
