package com.storyshare.repository;

import com.storyshare.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface CityRepository extends JpaRepository<CityEntity, UUID>, PagingAndSortingRepository<CityEntity, UUID>, JpaSpecificationExecutor<CityEntity> {
}
