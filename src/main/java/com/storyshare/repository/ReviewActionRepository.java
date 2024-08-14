package com.storyshare.repository;

import com.storyshare.enums.ReviewActionType;
import org.springframework.data.jpa.repository.JpaRepository;
import com.storyshare.entity.ReviewActionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewActionRepository extends JpaRepository<ReviewActionEntity, UUID> {
    ReviewActionEntity findByUserIdAndReviewId(UUID userId, UUID reviewId);

    @Query("select ra from ReviewActionEntity ra where ra.review.id= :reviewID")
    List<ReviewActionEntity> findAllByReviewId(@Param("reviewID") UUID reviewID);
}
