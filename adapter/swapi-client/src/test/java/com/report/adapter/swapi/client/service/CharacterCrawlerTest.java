package com.report.adapter.swapi.client.service;

import com.report.adapter.swapi.client.vo.EndpointName;
import com.report.adapter.swapi.client.vo.Result;
import com.report.application.entity.Character;
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
class CharacterCrawlerTest {
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
                        () -> new CharacterCrawler(null, null)),
                () -> assertThrows(NullPointerException.class,
                        () -> new CharacterCrawler(resultCrawler, null)),
                () -> assertThrows(NullPointerException.class,
                        () -> new CharacterCrawler(null, modelMapper))
        );
    }

    @Test
    @DisplayName("Creating with required objects")
    void shouldAcceptNeededObjectsInConstructor() {
        // When & Then
        assertDoesNotThrow(
                () -> new CharacterCrawler(resultCrawler, modelMapper)
        );
    }

    @Test
    @DisplayName("Crawling endpoint, when there are some results")
    void shouldReturnListOfCharacters() {
        // Given
        CharacterCrawler characterCrawler = new CharacterCrawler(resultCrawler, modelMapper);
        Result result = mock(Result.class);
        Character character = mock(Character.class);

        when(resultCrawler.crawl(any(EndpointName.class)))
                .thenReturn(Collections.singletonList(result));

        when(modelMapper.map(result, Character.class))
                .thenReturn(character);

        // When
        List<Character> characters = characterCrawler.crawl();

        // Then
        assertNotNull(characters);
        assertTrue(characters.contains(character));
    }

    @Test
    @DisplayName("Crawling endpoint, when there aren't some results")
    void shouldReturnEmptyList() {
        // Given
        CharacterCrawler characterCrawler = new CharacterCrawler(resultCrawler, modelMapper);

        when(resultCrawler.crawl(any(EndpointName.class)))
                .thenReturn(Collections.emptyList());

        // When
        List<Character> characters = characterCrawler.crawl();

        // Then
        assertNotNull(characters);
        assertTrue(characters.isEmpty());
    }
}
