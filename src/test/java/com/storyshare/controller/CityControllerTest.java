package com.storyshare.controller;

import com.storyshare.dto.criteria.CityCriteriaRequest;
import com.storyshare.dto.request.CityRequest;
import com.storyshare.dto.response.CityResponse;
import com.storyshare.entity.Translation;
import com.storyshare.service.CityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

class CityControllerTest {

    @Mock
    private CityService cityService;

    @InjectMocks
    private CityController cityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCity() {
        CityRequest mockCityRequest = new CityRequest();
        mockCityRequest.setName("Sample City");
        mockCityRequest.setParentCity(false);
        mockCityRequest.setParentId(UUID.randomUUID());
        mockCityRequest.setTranslations(List.of(new Translation("en", "Sample City")));

        cityController.addCity(mockCityRequest);

        verify(cityService).addCity(any(CityRequest.class));
    }

    @Test
    void testGetCity() {
        UUID cityId = UUID.randomUUID();
        CityResponse mockCityResponse = new CityResponse();
        mockCityResponse.setId(cityId);
        mockCityResponse.setName("Sample City");
        mockCityResponse.setParentCity(false);
        mockCityResponse.setStoryCount(5);
        mockCityResponse.setTranslations(List.of(new Translation("en", "Sample City")));

        when(cityService.getCity(any(UUID.class))).thenReturn(mockCityResponse);

        CityResponse result = cityController.getCity(cityId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockCityResponse, result);
        Assertions.assertEquals(cityId, result.getId());
        Assertions.assertEquals("Sample City", result.getName());
    }

    @Test
    void testGetAllCities() {
        CityResponse mockCityResponse = new CityResponse();
        mockCityResponse.setId(UUID.randomUUID());
        mockCityResponse.setName("Sample City");
        mockCityResponse.setParentCity(false);
        mockCityResponse.setStoryCount(5);
        mockCityResponse.setTranslations(List.of(new Translation("en", "Sample City")));

        when(cityService.getAllCities(any(Pageable.class), any(CityCriteriaRequest.class)))
                .thenReturn(List.of(mockCityResponse));

        List<CityResponse> result = cityController.getAllCities(Pageable.unpaged(), new CityCriteriaRequest());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(mockCityResponse, result.get(0));
    }

    @Test
    void testUpdateCity() {
        UUID cityId = UUID.randomUUID();
        CityRequest mockCityRequest = new CityRequest();
        mockCityRequest.setName("Updated City");
        mockCityRequest.setParentCity(true);
        mockCityRequest.setParentId(UUID.randomUUID());
        mockCityRequest.setTranslations(List.of(new Translation("en", "Updated City")));

        CityResponse mockCityResponse = new CityResponse();
        mockCityResponse.setId(cityId);
        mockCityResponse.setName("Updated City");
        mockCityResponse.setParentCity(true);
        mockCityResponse.setStoryCount(10);
        mockCityResponse.setTranslations(List.of(new Translation("en", "Updated City")));

        when(cityService.updateCity(any(UUID.class), any(CityRequest.class))).thenReturn(mockCityResponse);

        CityResponse result = cityController.updateCity(cityId, mockCityRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockCityResponse, result);
        Assertions.assertEquals(cityId, result.getId());
        Assertions.assertEquals("Updated City", result.getName());
    }

    @Test
    void testDeleteCity() {
        UUID cityId = UUID.randomUUID();

        cityController.deleteCity(cityId);

        verify(cityService).deleteCity(any(UUID.class));
    }
}