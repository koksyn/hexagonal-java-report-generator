package com.report.adapter.swapi.client.vo;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UrlIteratorTest {
    @Mock
    private Iterator<JsonNode> iterator;

    @Test
    @DisplayName("Creating with null value")
    void shouldNotAcceptNullValue() {
        // When & Then
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new UrlIterator(null)
        );

        assertEquals("raw is marked non-null but is null", exception.getMessage());
    }

    @Test
    @DisplayName("Creating with json node")
    void shouldAcceptJsonNode() {
        // When & Then
        assertDoesNotThrow(
                () -> new UrlIterator(iterator)
        );
    }

    @Test
    @DisplayName("Getting access to Raw value")
    void shouldGiveSameRawValueAsProvided() {
        // Given
        UrlIterator urlIterator = new UrlIterator(iterator);

        // When
        Iterator<JsonNode> raw = urlIterator.getRaw();

        // Then
        assertEquals(iterator, raw);
    }
}
