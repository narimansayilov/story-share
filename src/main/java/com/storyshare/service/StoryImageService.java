package com.storyshare.service;

import com.storyshare.dto.request.StoryImageRequest;
import com.storyshare.dto.response.StoryImageResponse;
import com.storyshare.entity.StoryEntity;
import com.storyshare.entity.StoryImageEntity;
import com.storyshare.mapper.StoryImageMapper;
import com.storyshare.repository.StoryImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoryImageService {
    private final AmazonS3Service s3Service;
    private final StoryImageRepository imageRepository;

    public void addImages(List<MultipartFile> images, UUID storyId) {
        boolean main = true;
        for (MultipartFile image : images) {
            StoryImageRequest imageRequest = StoryImageRequest.builder()
                    .storyId(storyId)
                    .url(s3Service.uploadFile(image))
                    .isMain(main)
                    .build();
            imageRepository.save(StoryImageMapper.INSTANCE.requestToEntity(imageRequest));
            main = !main;
        }
    }

    public void editImages(List<MultipartFile> images, UUID storyId) {
        List<StoryImageEntity> imageEntities = imageRepository.findByStoryIdAndStatus(storyId, true);
        imageEntities.forEach(entity -> entity.setStatus(false));
        imageRepository.saveAll(imageEntities);
        addImages(images, storyId);
    }

    public List<StoryImageResponse> getImages(UUID storyId) {
        List<StoryImageEntity> entities = imageRepository.findByStoryIdAndStatus(storyId, true);
        return StoryImageMapper.INSTANCE.entitiesToResponses(entities);
    }
}
