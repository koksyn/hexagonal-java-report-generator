package com.report.adapter.swapi.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.report.adapter.swapi.client.vo.LongIdSet;
import com.report.adapter.swapi.client.vo.Result;
import com.report.adapter.swapi.client.vo.UrlIterator;
import com.report.application.entity.Planet;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResultToPlanetConverterTest {
    private static final Long FILM_ID = 2L;
    private static final Long CHARACTER_ID = 3L;
    private static final Long PLANET_ID = 4L;
    private static final String PLANET_NAME = "Earth";
    private static final String URL = "https://swapi.co/api/planets/4/";

    @Mock
    private LongIdSet longIdSet;

    @Mock
    private JsonNode node;

    @Mock
    private Iterator<JsonNode> nodes;

    @InjectMocks
    private Result result;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ResultToPlanetConverter converter;

    @Captor
    private ArgumentCaptor<UrlIterator> captor;

    @Test
    @DisplayName("Converting null")
    void shouldReturnNull() {
        // When
        Planet planet = converter.convert((Result) null);

        // Then
        assertNull(planet);
    }

    @Test
    @DisplayName("Converting result")
    void shouldReturnPlanet() {
        // Given
        when(node.get(any(String.class)))
                .thenReturn(node, node, node);

        when(node.asText())
                .thenReturn(URL, PLANET_NAME);

        when(modelMapper.map(URL, Long.class))
                .thenReturn(PLANET_ID);

        when(node.elements())
                .thenReturn(nodes, nodes);

        when(modelMapper.map(any(UrlIterator.class), eq(LongIdSet.class)))
                .thenReturn(longIdSet, longIdSet);

        when(longIdSet.getRaw())
                .thenReturn(
                        Collections.singleton(FILM_ID),
                        Collections.singleton(CHARACTER_ID)
                );

        // When
        Planet planet = converter.convert(result);

        // Then
        assertEquals(PLANET_ID, planet.getId());
        assertEquals(PLANET_NAME, planet.getName());
        assertTrue(planet.getFilmIds()
                .contains(FILM_ID));
        assertTrue(planet.getCharacterIds()
                .contains(CHARACTER_ID));

        verify(modelMapper).map(URL, Long.class);

        verify(modelMapper, times(2))
                .map(captor.capture(), eq(LongIdSet.class));

        List<UrlIterator> urlIterators = captor.getAllValues();
        assertEquals(2, urlIterators.size());

        for(UrlIterator urlIterator : urlIterators) {
            assertEquals(nodes, urlIterator.getRaw());
        }
    }
}
