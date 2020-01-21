package com.report.adapter.swapi.client.service;

import com.report.adapter.swapi.client.vo.EndpointName;
import com.report.adapter.swapi.client.vo.Result;
import com.report.application.entity.Planet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlanetCrawlerTest {
    @Mock
    private ResultCrawler resultCrawler;
    @Mock
    private ModelMapper modelMapper;

    @Test
    @DisplayName("Creating with null values")
    void shouldNotAcceptNullValues() {
        // When & Then
        assertAll(
                () -> assertThrows(NullPointerException.class,
                        () -> new PlanetCrawler(null, null)),
                () -> assertThrows(NullPointerException.class,
                        () -> new PlanetCrawler(resultCrawler, null)),
                () -> assertThrows(NullPointerException.class,
                        () -> new PlanetCrawler(null, modelMapper))
        );
    }

    @Test
    @DisplayName("Creating with required objects")
    void shouldAcceptNeededObjectsInConstructor() {
        // When & Then
        assertDoesNotThrow(
                () -> new PlanetCrawler(resultCrawler, modelMapper)
        );
    }

    @Test
    @DisplayName("Crawling endpoint, when there are some results")
    void shouldReturnListOfPlanets() {
        // Given
        PlanetCrawler planetCrawler = new PlanetCrawler(resultCrawler, modelMapper);
        Result result = mock(Result.class);
        Planet planet = mock(Planet.class);

        when(resultCrawler.crawl(any(EndpointName.class)))
                .thenReturn(Collections.singletonList(result));

        when(modelMapper.map(result, Planet.class))
                .thenReturn(planet);

        // When
        List<Planet> planets = planetCrawler.crawl();

        // Then
        assertNotNull(planets);
        assertTrue(planets.contains(planet));
    }

    @Test
    @DisplayName("Crawling endpoint, when there aren't some results")
    void shouldReturnEmptyList() {
        // Given
        PlanetCrawler planetCrawler = new PlanetCrawler(resultCrawler, modelMapper);

        when(resultCrawler.crawl(any(EndpointName.class)))
                .thenReturn(Collections.emptyList());

        // When
        List<Planet> planets = planetCrawler.crawl();

        // Then
        assertNotNull(planets);
        assertTrue(planets.isEmpty());
    }
}
