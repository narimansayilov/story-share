package com.storyshare.service;

import com.storyshare.dto.criteria.CityCriteriaRequest;
import com.storyshare.dto.request.CityRequest;
import com.storyshare.dto.response.CityResponse;
import com.storyshare.entity.CityEntity;
import com.storyshare.exception.DeletionException;
import com.storyshare.exception.NotFoundException;
import com.storyshare.mapper.CityMapper;
import com.storyshare.repository.CityRepository;
import com.storyshare.service.Specification.CitySpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CItyService {
    private final CityRepository cityRepository;

    public void addCity(CityRequest request){
        if(request.getParentId() != null){
            CityEntity parent = cityRepository.findById(request.getParentId()).orElseThrow(() ->
                    new NotFoundException("PARENT_CITY_NOT_FOUND"));
            parent.setSubCityCount(parent.getSubCityCount() + 1);
        }
        CityEntity entity = CityMapper.INSTANCE.requestToEntity(request);
        cityRepository.save(entity);
    }

    public CityResponse getCity(UUID id){
        CityEntity cityEntity = cityRepository.findById(id).orElseThrow(() ->
                new NotFoundException("CITY_NOT_FOUND"));
        return CityMapper.INSTANCE.entityToResponse(cityEntity);
    }

    public List<CityResponse> getAllCities(Pageable pageable, CityCriteriaRequest criteriaRequest){
        Specification<CityEntity> specification = CitySpecification.getCityByCriteria(criteriaRequest);
        Page<CityEntity> entities = cityRepository.findAll(specification, pageable);
        return CityMapper.INSTANCE.entitiesToResponses(entities);
    }

    public CityResponse updateCity(UUID id, CityRequest request){
        CityEntity entity = cityRepository.findById(id).orElseThrow(() -> new NotFoundException("CITY_NOT_FOUND"));
        CityMapper.INSTANCE.mapRequestToEntity(entity, request);
        cityRepository.save(entity);
        return CityMapper.INSTANCE.entityToResponse(entity);
    }

    public void deleteCity(UUID id){
        CityEntity entity = cityRepository.findById(id).orElseThrow(() -> new NotFoundException("CITY_NOT_FOUND"));
        if(entity.getStoryCount() != 0){
            throw new DeletionException("TAG_HAS_ASSOCIATED_STORIES");
        }
        if(entity.getSubCityCount() != 0){
            throw new DeletionException("TAG_HAS_ASSOCIATED_SUB_CITIES");
        }
        cityRepository.deleteById(id);
    }
}
