package com.report.application.service;

import com.report.common.vo.PositiveInteger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ExecuteServiceFactoryTest {
    private static ExecutorServiceFactory factory;

    @BeforeAll
    static void init() {
        factory = new ExecutorServiceFactory();
    }

    @Test
    @DisplayName("Building with null size")
    void shouldNotAcceptNull() {
        // When & Then
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> factory.buildWithSize(null)
        );

        assertTrue(exception.getMessage()
                .contains("is marked non-null but is null"));
    }

    @ParameterizedTest
    @MethodSource("positivePoolSizes")
    @DisplayName("Building with positive pool size")
    void shouldAccept(Integer size) {
        // Given
        PositiveInteger poolSize = new PositiveInteger(size);

        // When
        ExecutorService result = factory.buildWithSize(poolSize);

        // Then
        ThreadPoolExecutor executor = (ThreadPoolExecutor) result;
        assertEquals(size, executor.getMaximumPoolSize());
    }

    private static Stream<Integer> positivePoolSizes() {
        return Stream.of(
            1,
            2,
            4
        );
    }
}
