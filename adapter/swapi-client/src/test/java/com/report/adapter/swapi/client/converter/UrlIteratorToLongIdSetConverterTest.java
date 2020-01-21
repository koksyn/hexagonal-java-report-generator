package com.report.adapter.swapi.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.report.adapter.swapi.client.vo.LongIdSet;
import com.report.adapter.swapi.client.vo.UrlIterator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlIteratorToLongIdSetConverterTest {
    private static final Long ID = 1337L;
    private static final String URL = "https://swapi.co/api/starships/1337/";

    @Mock
    private JsonNode node;

    @Mock
    private Iterator<JsonNode> nodeIterator;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UrlIteratorToLongIdSetConverter converter;

    @Test
    @DisplayName("Converting null")
    void shouldReturnNull() {
        // When
        LongIdSet longIdSet = converter.convert((UrlIterator) null);

        // Then
        assertNull(longIdSet);
    }

    @Test
    @DisplayName("Converting URL iterator when it hasn't any nodes")
    void shouldReturnEmptyLongIdSet() {
        // Given
        UrlIterator urlIterator = new UrlIterator(nodeIterator);

        when(nodeIterator.hasNext()).thenReturn(false);

        // When
        LongIdSet longIdSet = converter.convert(urlIterator);

        // Then
        assertNotNull(longIdSet);
        assertTrue(longIdSet.getRaw()
                .isEmpty());
    }

    @Test
    @DisplayName("Converting URL iterator when it has node")
    void shouldReturnLongIdSet() {
        // Given
        UrlIterator urlIterator = new UrlIterator(nodeIterator);

        when(nodeIterator.hasNext())
                .thenReturn(true, false);

        when(nodeIterator.next())
                .thenReturn(node);

        when(node.asText())
                .thenReturn(URL);

        when(modelMapper.map(URL, Long.class))
                .thenReturn(ID);

        // When
        LongIdSet longIdSet = converter.convert(urlIterator);

        // Then
        assertNotNull(longIdSet);
        Set<Long> ids = longIdSet.getRaw();

        assertTrue(ids.contains(ID));
    }
}
