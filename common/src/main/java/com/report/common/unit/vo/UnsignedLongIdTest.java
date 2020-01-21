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
}
