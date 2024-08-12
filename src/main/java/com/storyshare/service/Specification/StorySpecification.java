package com.storyshare.service.Specification;

import com.storyshare.dto.criteria.StoryCriteriaRequest;
import com.storyshare.entity.StoryEntity;
import com.storyshare.entity.TagEntity;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StorySpecification implements Specification<StoryEntity> {

    public static Specification<StoryEntity> getStoryByCriteria(StoryCriteriaRequest criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = buildPredicates(criteria, root, criteriaBuilder);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<StoryEntity> getMyStoryByCriteria(StoryCriteriaRequest criteria, UUID userId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = buildPredicates(criteria, root, criteriaBuilder);

            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), userId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static List<Predicate> buildPredicates(StoryCriteriaRequest criteria, Root<StoryEntity> root, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getTitle() != null && !criteria.getTitle().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + criteria.getTitle().toLowerCase() + "%"));
        }

        if (criteria.getDescription() != null && !criteria.getDescription().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + criteria.getDescription().toLowerCase() + "%"));
        }

        if (criteria.getCityId() != null) {
            Predicate cityIdPredicate = criteriaBuilder.equal(root.get("city").get("id"), criteria.getCityId());
            Predicate parentCityIdPredicate = criteriaBuilder.equal(root.get("city").get("parent").get("id"), criteria.getCityId());
            predicates.add(criteriaBuilder.or(cityIdPredicate, parentCityIdPredicate));
        }

        if (criteria.getTagIds() != null && !criteria.getTagIds().isEmpty()) {
            Join<StoryEntity, TagEntity> tags = root.join("tags");
            predicates.add(tags.get("id").in(criteria.getTagIds()));
        }

        return predicates;
    }

    @Override
    public Predicate toPredicate(Root<StoryEntity> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
