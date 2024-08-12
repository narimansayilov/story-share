package com.storyshare.service;

import com.storyshare.dto.criteria.StoryCriteriaRequest;
import com.storyshare.dto.request.StoryRequest;
import com.storyshare.dto.response.StoryImageResponse;
import com.storyshare.dto.response.StoryResponse;
import com.storyshare.entity.CityEntity;
import com.storyshare.entity.StoryEntity;
import com.storyshare.entity.TagEntity;
import com.storyshare.entity.UserEntity;
import com.storyshare.exception.ActiveException;
import com.storyshare.exception.NotActiveException;
import com.storyshare.exception.NotFoundException;
import com.storyshare.exception.UnauthorizedAccessException;
import com.storyshare.mapper.StoryMapper;
import com.storyshare.repository.CityRepository;
import com.storyshare.repository.StoryRepository;
import com.storyshare.repository.TagRepository;
import com.storyshare.repository.UserRepository;
import com.storyshare.service.Specification.StorySpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoryService {
    private final UserService userService;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final StoryImageService imageService;
    private final StoryRepository storyRepository;
    private final CityRepository cityRepository;
    private final StoryImageService storyImageService;

    @Transactional
    public void addStory(StoryRequest request, List<MultipartFile> images) {
        UserEntity user = userRepository.findByUsername(userService.getCurrentUsername()).orElseThrow(() ->
                new NotFoundException("USER_NOT_FOUND"));
        List<TagEntity> tags = tagRepository.findAllById(request.getTagIds());
        StoryEntity entity = StoryMapper.INSTANCE.requestToEntity(request, tags);
        entity.setUser(user);
        storyRepository.save(entity);
        imageService.addImages(images, entity.getId());
        setCount(user, request.getCityId(), request.getTagIds());
    }

    @Transactional
    public StoryResponse getStory(UUID id) {
        StoryEntity entity = storyRepository.findById(id).orElseThrow(() ->
                new NotFoundException("STORY_NOT_FOUND"));
        List<StoryImageResponse> images = storyImageService.getImages(id);
        StoryResponse response = StoryMapper.INSTANCE.entityToResponse(entity, images);
        entity.setViewCount(entity.getViewCount() + 1);
        storyRepository.save(entity);
        return response;
    }

    public List<StoryResponse> getAllStories(Pageable pageable, StoryCriteriaRequest criteriaRequest) {
        Specification<StoryEntity> specification = StorySpecification.getStoryByCriteria(criteriaRequest);
        Page<StoryEntity> entities = storyRepository.findAll(specification, pageable);
        return StoryMapper.INSTANCE.entitiesToResponses(entities);
    }

    public List<StoryResponse> getMyStories(Pageable pageable, StoryCriteriaRequest criteriaRequest) {
        UserEntity user = userRepository.findByUsername(userService.getCurrentUsername()).orElseThrow(() ->
                new NotFoundException("USER_NOT_FOUND"));
        Specification<StoryEntity> specification = StorySpecification.getMyStoryByCriteria(criteriaRequest, user.getId());
        Page<StoryEntity> entities = storyRepository.findAll(specification, pageable);
        return StoryMapper.INSTANCE.entitiesToResponses(entities);
    }

    @Transactional
    public StoryResponse updateStory(UUID id, StoryRequest request, List<MultipartFile> images) {
        StoryEntity entity = storyRepository.findById(id).orElseThrow(() ->
                new NotFoundException("STORY_NOT_FOUND"));
        if(!entity.getUser().getUsername().equals(userService.getCurrentUsername())) {
            throw new UnauthorizedAccessException("UNAUTHORIZED_ACCESS");
        }
        if(!entity.getStatus()){
            throw new NotActiveException("STORY_NOT_ACTIVE");
        }
        StoryMapper.INSTANCE.mapRequestToEntity(entity, request);
        storyRepository.save(entity);
        imageService.editImages(images, id);
        List<StoryImageResponse> imageResponses = storyImageService.getImages(id);
        return StoryMapper.INSTANCE.entityToResponse(entity, imageResponses);
    }

    public void activateStory(UUID id) {
        StoryEntity entity = storyRepository.findById(id).orElseThrow(() ->
                new ActiveException("ACTIVE_EXCEPTION"));
        if(!entity.getUser().getUsername().equals(userService.getCurrentUsername())) {
            throw new UnauthorizedAccessException("UNAUTHORIZED_ACCESS");
        }
        if(entity.getStatus()){
            throw new ActiveException("STORY_ACTIVE");
        }
        entity.setStatus(true);
        storyRepository.save(entity);
    }

    public void deactivateStory(UUID id) {
        StoryEntity entity = storyRepository.findById(id).orElseThrow(() ->
                new ActiveException("ACTIVE_EXCEPTION"));
        if(!entity.getUser().getUsername().equals(userService.getCurrentUsername())) {
            throw new UnauthorizedAccessException("UNAUTHORIZED_ACCESS");
        }
        if(!entity.getStatus()){
            throw new NotActiveException("STORY_NOT_ACTIVE");
        }
        entity.setStatus(false);
        storyRepository.save(entity);
    }

    private void setCount(UserEntity user, UUID cityId, List<UUID> tagIds){
        if(!user.getStatus().equals(true)){
            throw new NotActiveException("USER_NOT_ACTIVE");
        }
        user.setStoryCount(user.getStoryCount() + 1);
        userRepository.save(user);

        CityEntity city = cityRepository.findById(cityId).orElseThrow(() ->
                new NotFoundException("CITY_NOT_FOUND"));
        city.setStoryCount(city.getStoryCount() + 1);
        cityRepository.save(city);

        List<TagEntity> tags = tagRepository.findAllById(tagIds);
        for(TagEntity tag : tags){
            tag.setStoryCount(tag.getStoryCount() + 1);
            tagRepository.save(tag);
        }
    }
}