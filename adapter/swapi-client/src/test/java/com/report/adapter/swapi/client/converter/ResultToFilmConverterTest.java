package com.report.adapter.swapi.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.report.adapter.swapi.client.vo.LongIdSet;
import com.report.adapter.swapi.client.vo.Result;
import com.report.adapter.swapi.client.vo.UrlIterator;
import com.report.application.entity.Film;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResultToFilmConverterTest {
    private static final Long FILM_ID = 2L;
    private static final Long CHARACTER_ID = 3L;
    private static final String FILM_NAME = "Mars Odyssey";
    private static final String URL = "https://swapi.co/api/films/2/";

    @Mock
    private LongIdSet longIdSet;

    @Mock
    private JsonNode node;

    @Mock
    private Iterator<JsonNode> characterNodes;

    @InjectMocks
    private Result result;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ResultToFilmConverter converter;

    @Captor
    private ArgumentCaptor<UrlIterator> captor;

    @Test
    @DisplayName("Converting null")
    void shouldReturnNull() {
        // When
        Film film = converter.convert((Result) null);

        // Then
        assertNull(film);
    }

    @Test
    @DisplayName("Converting result")
    void shouldReturnFilm() {
        // Given
        when(node.get(any(String.class)))
                .thenReturn(node, node, node);

        when(node.asText())
                .thenReturn(URL, FILM_NAME);

        when(modelMapper.map(URL, Long.class))
                .thenReturn(FILM_ID);

        when(node.elements())
                .thenReturn(characterNodes);

        when(modelMapper.map(any(UrlIterator.class), eq(LongIdSet.class)))
                .thenReturn(longIdSet);

        when(longIdSet.getRaw())
                .thenReturn(Collections.singleton(CHARACTER_ID));

        // When
        Film film = converter.convert(result);

        // Then
        assertEquals(FILM_ID, film.getId());
        assertEquals(FILM_NAME, film.getName());
        assertTrue(film.getCharactersIds()
                .contains(CHARACTER_ID));

        verify(modelMapper).map(URL, Long.class);
        verify(modelMapper).map(captor.capture(), eq(LongIdSet.class));

        UrlIterator urlIterator = captor.getValue();
        assertNotNull(urlIterator);
        assertEquals(characterNodes, urlIterator.getRaw());
    }
}
