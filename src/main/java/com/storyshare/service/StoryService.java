package com.storyshare.service;

import com.storyshare.dto.request.StoryRequest;
import com.storyshare.dto.response.StoryImageResponse;
import com.storyshare.dto.response.StoryResponse;
import com.storyshare.entity.CityEntity;
import com.storyshare.entity.StoryEntity;
import com.storyshare.entity.TagEntity;
import com.storyshare.entity.UserEntity;
import com.storyshare.exception.NotActiveException;
import com.storyshare.exception.NotFoundException;
import com.storyshare.mapper.StoryMapper;
import com.storyshare.repository.CityRepository;
import com.storyshare.repository.StoryRepository;
import com.storyshare.repository.TagRepository;
import com.storyshare.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
