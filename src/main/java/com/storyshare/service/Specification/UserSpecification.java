package com.storyshare.service.Specification;

import com.storyshare.dto.criteria.TagCriteriaRequest;
import com.storyshare.dto.criteria.UserCriteriaRequest;
import com.storyshare.entity.TagEntity;
import com.storyshare.entity.UserEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification implements Specification<UserEntity> {
    public static Specification<UserEntity> getUserByCriteria(UserCriteriaRequest criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getUsername() != null && !criteria.getUsername().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%" + criteria.getUsername().toLowerCase() + "%"));
            }

            predicates.add(criteriaBuilder.isTrue(root.get("status")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}