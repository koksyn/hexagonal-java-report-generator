package com.report.common.unit.vo;

import com.google.common.primitives.UnsignedLong;
import com.report.common.vo.UnsignedLongId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public abstract class UnsignedLongIdTest<T extends UnsignedLongId> extends com.report.common.unit.InstanceFactory<T> {
    private static final Class[] argumentTypes = new Class[]{UnsignedLong.class};

    @Test
    @DisplayName("Creating with null value")
    void shouldNotAcceptNullValue() {
        // When & Then
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> newInstance(argumentTypes, (UnsignedLong) null)
        );

        assertEquals("raw is marked non-null but is null", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("validIds")
    @DisplayName("Creating with valid IDs")
    void shouldAcceptIDs(UnsignedLong id) {
        // When & Then
        assertDoesNotThrow(
            () -> newInstance(argumentTypes, id)
        );
    }

    private static Stream<UnsignedLong> validIds() {
        return Stream.of(
            UnsignedLong.ZERO,
            UnsignedLong.ONE,
            UnsignedLong.MAX_VALUE
        );
    }

    @Test
    @DisplayName("Getting access to Raw value")
    void shouldGiveSameRawValueAsProvided() {
        // When
        T id = newInstance(argumentTypes, UnsignedLong.ONE);

        // Then
        assertEquals(UnsignedLong.ONE, id.getRaw());
    }


    @ParameterizedTest
    @MethodSource("differentObjects")
    @DisplayName("Comparing with different objects")
    void shouldReturnFalse(Object differentObject) {
        // Given
        T id = newInstance(argumentTypes, UnsignedLong.ONE);

        // When
        boolean result = id.equals(differentObject);

        // Then
        assertFalse(result);
    }

    private static Stream<Object> differentObjects() {
        return Stream.of(
                null,
                1,
                "abc"
        );
    }

    @Test
    @DisplayName("Comparing with object with different properties inside")
    void shouldAlsoReturnFalse() {
        // Given
        T first = newInstance(argumentTypes, UnsignedLong.ONE);
        T second = newInstance(argumentTypes, UnsignedLong.ZERO);

        // When
        boolean result = first.equals(second);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Comparing with the same object")
    void shouldReturnTrue() {
        // Given
        T id = newInstance(argumentTypes, UnsignedLong.ONE);

        // When
        boolean result = id.equals(id);

        // Then
        assertTrue(result);
    }
}
