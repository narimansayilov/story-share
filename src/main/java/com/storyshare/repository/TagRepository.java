package com.storyshare.repository;

import com.storyshare.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface TagRepository extends JpaRepository<TagEntity, UUID>, PagingAndSortingRepository<TagEntity, UUID>, JpaSpecificationExecutor<TagEntity> {
}
