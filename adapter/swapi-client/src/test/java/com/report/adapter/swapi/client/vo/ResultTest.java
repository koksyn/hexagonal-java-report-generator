package com.report.adapter.swapi.client.vo;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ResultTest {
    @Mock
    private JsonNode jsonNode;

    @Test
    @DisplayName("Creating with null value")
    void shouldNotAcceptNullValue() {
        // When & Then
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new Result(null)
        );

        assertEquals("raw is marked non-null but is null", exception.getMessage());
    }

    @Test
    @DisplayName("Creating with json node")
    void shouldAcceptJsonNode() {
        // When & Then
        assertDoesNotThrow(
                () -> new Result(jsonNode)
        );
    }

    @Test
    @DisplayName("Getting access to Raw value")
    void shouldGiveSameRawValueAsProvided() {
        // Given
        Result result = new Result(jsonNode);

        // When
        JsonNode raw = result.getRaw();

        // Then
        assertEquals(jsonNode, raw);
    }
}
