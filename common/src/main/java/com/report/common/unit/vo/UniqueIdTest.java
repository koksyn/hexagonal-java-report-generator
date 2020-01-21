package com.report.common.unit.vo;

import com.report.common.vo.UniqueId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public abstract class UniqueIdTest<T extends UniqueId> extends com.report.common.unit.InstanceFactory<T> {
    private static final Class[] argumentTypes = new Class[]{UUID.class};

    @Test
    @DisplayName("Creating with null value")
    void shouldNotAcceptNullValue() {
        // When & Then
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> newInstance(argumentTypes, (UUID) null)
        );

        assertEquals("raw is marked non-null but is null", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("validIds")
    @DisplayName("Creating with valid UUIDs")
    void shouldAcceptUUIDs(UUID uuid) {
        // When & Then
        assertDoesNotThrow(
            () -> newInstance(argumentTypes, uuid)
        );
    }

    private static Stream<UUID> validIds() {
        return Stream.of(
                UUID.fromString("7f83f083-675d-4208-aaa9-af3786350ded"),
                UUID.randomUUID()
        );
    }

    @Test
    @DisplayName("Getting access to Raw value")
    void shouldGiveSameRawValueAsProvided() {
        // Given
        UUID randomId = UUID.randomUUID();

        // When
        T id = newInstance(argumentTypes, randomId);

        // Then
        assertEquals(randomId, id.getRaw());
    }

    @Test
    @DisplayName("Getting access to Raw value for instance created by default constructor")
    void shouldGiveNonNullRawValueForInstanceCreatedByDefaultConstructor() {
        // When
        T id = newInstance();

        // Then
        assertNotNull(id.getRaw());
    }
}
