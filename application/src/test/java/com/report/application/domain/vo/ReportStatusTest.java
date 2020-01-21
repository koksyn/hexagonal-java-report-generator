package com.report.application.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ReportStatusTest {
    @Test
    @DisplayName("Creating with null value")
    void shouldNotAcceptNullValue() {
        // When & Then
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> new ReportStatus(null)
        );

        assertEquals("raw is marked non-null but is null", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("validStatuses")
    @DisplayName("Creating with valid statuses")
    void shouldAcceptValidStatuses(com.report.application.domain.type.ReportStatus status) {
        // When & Then
        assertDoesNotThrow(
            () -> new ReportStatus(status)
        );
    }

    private static Stream<com.report.application.domain.type.ReportStatus> validStatuses() {
        return Stream.of(
                com.report.application.domain.type.ReportStatus.COMPLETE,
                com.report.application.domain.type.ReportStatus.INCOMPLETE
        );
    }

    @Test
    @DisplayName("Getting access to Raw value")
    void shouldGiveSameRawValueAsProvided() {
        // When
        ReportStatus reportStatus = new ReportStatus(com.report.application.domain.type.ReportStatus.COMPLETE);

        // Then
        assertEquals(com.report.application.domain.type.ReportStatus.COMPLETE, reportStatus.getRaw());
    }
}
