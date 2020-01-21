package com.report.adapter.swapi.client.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LongIdSetTest {
    @Mock
    private Set<Long> longSet;

    @Test
    @DisplayName("Creating with null value")
    void shouldNotAcceptNullValue() {
        // When & Then
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new LongIdSet(null)
        );

        assertEquals("raw is marked non-null but is null", exception.getMessage());
    }

    @Test
    @DisplayName("Creating with long Set")
    void shouldAcceptLongSet() {
        // When & Then
        assertDoesNotThrow(
                () -> new LongIdSet(longSet)
        );
    }

    @Test
    @DisplayName("Getting access to Raw value")
    void shouldGiveSameRawValueAsProvided() {
        // Given
        LongIdSet longIdSet = new LongIdSet(longSet);

        // When
        Set<Long> results = longIdSet.getRaw();

        // Then
        assertEquals(longSet, results);
    }
}
