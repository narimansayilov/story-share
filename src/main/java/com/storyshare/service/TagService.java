package com.storyshare.service;

import com.storyshare.dto.criteria.TagCriteriaRequest;
import com.storyshare.dto.request.TagRequest;
import com.storyshare.dto.response.TagResponse;
import com.storyshare.entity.TagEntity;
import com.storyshare.exception.DeletionException;
import com.storyshare.exception.NotFoundException;
import com.storyshare.mapper.TagMapper;
import com.storyshare.repository.TagRepository;
import com.storyshare.service.Specification.TagSpecification;
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
public class TagService {
    private final TagRepository tagRepository;

    public void addTag(TagRequest request){
        tagRepository.save(TagMapper.INSTANCE.requestToEntity(request));
    }

    public TagResponse getTag(UUID id){
        TagEntity entity = tagRepository.findById(id).orElseThrow(() ->
                new NotFoundException("TAG_NOT_FOUND"));
        return TagMapper.INSTANCE.entityToResponse(entity);
    }

    public List<TagResponse> getAllTags(Pageable pageable, TagCriteriaRequest criteriaRequest){
        Specification<TagEntity> specification = TagSpecification.getTagByCriteria(criteriaRequest);
        Page<TagEntity> entities = tagRepository.findAll(specification, pageable);
        return TagMapper.INSTANCE.entitiesToResponses(entities);
    }

    public TagResponse updateTag(UUID id, TagRequest request){
        TagEntity entity = tagRepository.findById(id).orElseThrow(() ->
                new NotFoundException("TAG_NOT_FOUND"));
        TagMapper.INSTANCE.mapRequestToEntity(entity, request);
        tagRepository.save(entity);
        return TagMapper.INSTANCE.entityToResponse(entity);
    }

    public void deleteTag(UUID id){
        TagEntity entity = tagRepository.findById(id).orElseThrow(() ->
                new NotFoundException("TAG_NOT_FOUND"));
        if(entity.getStoryCount() != 0){
            throw new DeletionException("TAG_HAS_ASSOCIATED_STORIES");
        }
        tagRepository.deleteById(id);
    }
}
