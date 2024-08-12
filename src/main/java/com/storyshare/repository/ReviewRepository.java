package com.storyshare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.storyshare.entity.ReviewEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, UUID> {
    List<ReviewEntity> findByParentId(UUID parentId);
}