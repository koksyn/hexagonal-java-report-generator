package com.report.common.unit.vo;

import com.report.common.exception.ValidationException;
import com.report.common.vo.NonBlankName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public abstract class NonBlankNameTest<T extends NonBlankName> extends com.report.common.unit.InstanceFactory<T> {
    private static final Class[] argumentTypes = new Class[]{String.class};

    @Test
    @DisplayName("Creating with null value")
    void shouldNotAcceptNullValue() {
        // When & Then
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> newInstance(argumentTypes, (String) null)
        );

        assertEquals("raw is marked non-null but is null", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("incorrectValues")
    @DisplayName("Creating with incorrect values")
    void shouldNotAcceptIncorrectValues(String raw) {
        // When & Then
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> newInstance(argumentTypes, raw)
        );

        assertTrue(exception
                .getMessage()
                .contains("should contain at least one character (not whitespace)")
        );
    }

    private static Stream<String> incorrectValues() {
        return Stream.of(
                "",
                " ",
                "      "
        );
    }

    @ParameterizedTest
    @MethodSource("correctValues")
    @DisplayName("Creating with correct values")
    void shouldAcceptCorrectValues(String raw) {
        // When & Then
        assertDoesNotThrow(
                () -> newInstance(argumentTypes, raw)
        );
    }

    private static Stream<String> correctValues() {
        return Stream.of(
                "Oi",
                "Warsaw",
                "Svalbarðsstrandarhreppur"
        );
    }

    @Test
    @DisplayName("Getting access to Raw value")
    void shouldGiveSameRawValueAsProvided() {
        // Given
        String raw = "Reykjavík";

        // When
        T nonBlankName = newInstance(argumentTypes, raw);

        // Then
        assertEquals(raw, nonBlankName.getRaw());
    }
}
