package com.storyshare.controller;

import com.storyshare.dto.criteria.CityCriteriaRequest;
import com.storyshare.dto.request.CityRequest;
import com.storyshare.dto.response.CityResponse;
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
    CityService cityService;
    @InjectMocks
    CityController cityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCity() {
        cityController.addCity(new CityRequest());
        verify(cityService).addCity(any(CityRequest.class));
    }

    @Test
    void testGetCity() {
        when(cityService.getCity(any(UUID.class))).thenReturn(new CityResponse());

        CityResponse result = cityController.getCity(new UUID(0L, 0L));
        Assertions.assertEquals(new CityResponse(), result);
    }

    @Test
    void testGetAllCities() {
        when(cityService.getAllCities(any(Pageable.class), any(CityCriteriaRequest.class))).thenReturn(List.of(new CityResponse()));

        List<CityResponse> result = cityController.getAllCities(null, new CityCriteriaRequest());
        Assertions.assertEquals(List.of(new CityResponse()), result);
    }

    @Test
    void testUpdateCity() {
        when(cityService.updateCity(any(UUID.class), any(CityRequest.class))).thenReturn(new CityResponse());

        CityResponse result = cityController.updateCity(new UUID(0L, 0L), new CityRequest());
        Assertions.assertEquals(new CityResponse(), result);
    }

    @Test
    void testDeleteCity() {
        cityController.deleteCity(new UUID(0L, 0L));
        verify(cityService).deleteCity(any(UUID.class));
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme