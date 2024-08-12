package com.storyshare.service.Specification;

import com.storyshare.dto.criteria.CityCriteriaRequest;
import com.storyshare.entity.CityEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CitySpecification implements Specification<CityEntity> {
    public static Specification<CityEntity> getCityByCriteria(CityCriteriaRequest criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getName() != null && !criteria.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + criteria.getName().toLowerCase() + "%"));
            }

            if (criteria.getParent() != null) {
                predicates.add(criteriaBuilder.equal(root.get("parentCity"), criteria.getParent()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public Predicate toPredicate(Root<CityEntity> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
