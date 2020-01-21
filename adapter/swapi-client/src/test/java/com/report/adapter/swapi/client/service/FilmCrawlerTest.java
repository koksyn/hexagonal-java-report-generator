package com.report.adapter.swapi.client.service;

import com.report.adapter.swapi.client.vo.EndpointName;
import com.report.adapter.swapi.client.vo.Result;
import com.report.application.entity.Film;
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
class FilmCrawlerTest {
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
                        () -> new FilmCrawler(null, null)),
                () -> assertThrows(NullPointerException.class,
                        () -> new FilmCrawler(resultCrawler, null)),
                () -> assertThrows(NullPointerException.class,
                        () -> new FilmCrawler(null, modelMapper))
        );
    }

    @Test
    @DisplayName("Creating with required objects")
    void shouldAcceptNeededObjectsInConstructor() {
        // When & Then
        assertDoesNotThrow(
                () -> new FilmCrawler(resultCrawler, modelMapper)
        );
    }

    @Test
    @DisplayName("Crawling endpoint, when there are some results")
    void shouldReturnListOfFilms() {
        // Given
        FilmCrawler filmCrawler = new FilmCrawler(resultCrawler, modelMapper);
        Result result = mock(Result.class);
        Film film = mock(Film.class);

        when(resultCrawler.crawl(any(EndpointName.class)))
                .thenReturn(Collections.singletonList(result));

        when(modelMapper.map(result, Film.class))
                .thenReturn(film);

        // When
        List<Film> films = filmCrawler.crawl();

        // Then
        assertNotNull(films);
        assertTrue(films.contains(film));
    }

    @Test
    @DisplayName("Crawling endpoint, when there aren't some results")
    void shouldReturnEmptyList() {
        // Given
        FilmCrawler filmCrawler = new FilmCrawler(resultCrawler, modelMapper);

        when(resultCrawler.crawl(any(EndpointName.class)))
                .thenReturn(Collections.emptyList());

        // When
        List<Film> films = filmCrawler.crawl();

        // Then
        assertNotNull(films);
        assertTrue(films.isEmpty());
    }
}
