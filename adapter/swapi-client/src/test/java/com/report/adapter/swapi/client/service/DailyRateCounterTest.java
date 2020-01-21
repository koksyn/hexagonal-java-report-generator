package com.report.adapter.swapi.client.service;

import com.report.common.exception.ReportException;
import com.report.common.vo.PositiveInteger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DailyRateCounterTest {
    private static final int REQUESTS_PER_DAY_LIMIT = 3;
    private static PositiveInteger requestsPerDayLimit;

    @Mock
    private Clock clock;

    @BeforeAll
    static void init() {
        requestsPerDayLimit = new PositiveInteger(REQUESTS_PER_DAY_LIMIT);
    }

    @Test
    @DisplayName("Creating with null values")
    void shouldNotAcceptNullValue() {
        // When & Then
        assertAll(
                () -> assertThrows(NullPointerException.class,
                        () -> new DailyRateCounter(null, null)),
                () -> assertThrows(NullPointerException.class,
                        () -> new DailyRateCounter(requestsPerDayLimit, null)),
                () -> assertThrows(NullPointerException.class,
                        () -> new DailyRateCounter(null, clock))
        );
    }

    @Test
    @DisplayName("Creating with required objects")
    void shouldAcceptNeededObjectsInConstructor() {
        // When & Then
        assertDoesNotThrow(
                () -> new DailyRateCounter(requestsPerDayLimit, clock)
        );
    }

    @Test
    @DisplayName("Incrementing, when all requirements are satisfied")
    void shouldIncrementWithoutExceptions() {
        // Given
        setClockToEpoch();
        DailyRateCounter dailyRateCounter = new DailyRateCounter(requestsPerDayLimit, clock);

        // When & Then
        assertDoesNotThrow(dailyRateCounter::increment);
    }

    @Test
    @DisplayName("Incrementing, when day has passed")
    void shouldResetRateAndBeAbleToIncrementManyTimesAgain() {
        // Given
        DailyRateCounter dailyRateCounter = buildCounterWithRateWithOneStepLeft();
        setClockToDayAfterEpoch();

        // When & Then
        assertDoesNotThrow(
                () -> incrementUntilOneStepBeforeLimitLeft(dailyRateCounter)
        );
    }

    @Test
    @DisplayName("Incrementing, when limit per day has reached")
    void shouldThrowException() {
        // Given
        DailyRateCounter dailyRateCounter = buildCounterWithRateWithOneStepLeft();

        // When & Then
        ReportException exception = assertThrows(
                ReportException.class,
                dailyRateCounter::increment
        );

        assertTrue(exception
                .getMessage()
                .contains("rate limit of API requests reached")
        );
    }

    private DailyRateCounter buildCounterWithRateWithOneStepLeft() {
        setClockToEpoch();

        DailyRateCounter dailyRateCounter = new DailyRateCounter(requestsPerDayLimit, clock);
        incrementUntilOneStepBeforeLimitLeft(dailyRateCounter);

        return dailyRateCounter;
    }

    private void setClockToEpoch() {
        setClockTime(Instant.EPOCH);
    }

    private void setClockToDayAfterEpoch() {
        setClockTime(Instant.EPOCH
                .plusSeconds(86_400));
    }

    private void setClockTime(Instant time) {
        when(clock.instant()).thenReturn(time);
    }

    private static void incrementUntilOneStepBeforeLimitLeft(DailyRateCounter dailyRateCounter) {
        for(int i = 0; i < REQUESTS_PER_DAY_LIMIT ; i++) {
            dailyRateCounter.increment();
        }
    }
}
