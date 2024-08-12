package com.storyshare.controller;

import com.storyshare.dto.criteria.CityCriteriaRequest;
import com.storyshare.dto.request.CityRequest;
import com.storyshare.dto.response.CityResponse;
import com.storyshare.service.CItyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cities")
public class CityController {
    private final CItyService cityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCity(@RequestBody CityRequest request){
        cityService.addCity(request);
    }

    @GetMapping("/{id}")
    public CityResponse getCity(@PathVariable UUID id){
        return cityService.getCity(id);
    }

    @GetMapping
    public List<CityResponse> getAllCities(Pageable pageable, CityCriteriaRequest criteriaRequest){
        return cityService.getAllCities(pageable, criteriaRequest);
    }

    @PutMapping("/{id}")
    public CityResponse updateCity(@PathVariable UUID id, @RequestBody CityRequest request){
        return cityService.updateCity(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteCity(@PathVariable UUID id){
        cityService.deleteCity(id);
    }
}
