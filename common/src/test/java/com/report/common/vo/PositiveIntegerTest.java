package com.report.common.vo;

import com.report.common.exception.ReportException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PositiveIntegerTest {
    @ParameterizedTest
    @MethodSource("nonPositiveNumbers")
    @DisplayName("Creating with non-positive numbers")
    void shouldNotAccept(Integer number) {
        // When & Then
        ReportException exception = assertThrows(
                ReportException.class,
                () -> new PositiveInteger(number)
        );

        assertTrue(exception.getMessage()
                .contains("should be a positive"));
    }

    private static Stream<Integer> nonPositiveNumbers() {
        return Stream.of(
                0,
                -1,
                -5,
                -1000,
                Integer.MIN_VALUE
        );
    }

    @ParameterizedTest
    @MethodSource("positiveNumbers")
    @DisplayName("Creating with positive numbers")
    void shouldAccept(Integer number) {
        // When & Then
        assertDoesNotThrow(
            () -> new PositiveInteger(number)
        );
    }

    private static Stream<Integer> positiveNumbers() {
        return Stream.of(
            1,
            2,
            5,
            1000,
            Integer.MAX_VALUE
        );
    }

    @Test
    @DisplayName("Getting access to Raw value")
    void shouldGiveSameRawValueAsProvided() {
        // When
        PositiveInteger number = new PositiveInteger(1337);

        // Then
        assertEquals(1337, number.getRaw());
    }
}
