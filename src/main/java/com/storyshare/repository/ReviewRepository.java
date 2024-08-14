package com.storyshare.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.storyshare.entity.ReviewEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, UUID> {
    @Query("select r from ReviewEntity r where r.id = :id and r.status = true")
    Optional<ReviewEntity> findById(@Param("id") UUID id);

    @Query("select r from ReviewEntity r where r.status = true")
    Page<ReviewEntity> findAllActiveReviews(Pageable pageable);

    List<ReviewEntity> findByParentId(UUID parentId);

    Page<ReviewEntity> findByStoryId(Pageable pageable, UUID storyId);
}