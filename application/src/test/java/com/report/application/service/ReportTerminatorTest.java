package com.report.application.service;

import com.report.application.domain.vo.ReportId;
import com.report.application.entity.Report;
import com.report.application.port.driven.ReportRepository;
import com.report.common.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportTerminatorTest {
    @Mock
    private ReportId reportId;
    @Mock
    private Report report;
    @Mock
    private ReportRepository repository;

    @InjectMocks
    private ReportTerminator reportTerminator;

    @Test
    @DisplayName("Deleting complete report, when ReportId is null")
    void shouldNotAcceptNull() {
        // When & Then
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> reportTerminator.delete(null)
        );

        assertTrue(exception
                .getMessage()
                .contains("is marked non-null but is null")
        );
    }

    @Test
    @DisplayName("Deleting complete report, when it does exist")
    void shouldDeleteCompleteViaRepository() {
        // Given
        when(repository.getComplete(reportId))
                .thenReturn(Optional.of(report));

        // When
        reportTerminator.delete(reportId);

        // Then
        verify(repository).deleteComplete(reportId);
    }

    @Test
    @DisplayName("Deleting complete report, when it does not exist")
    void shouldThrowException() {
        // Given
        when(repository.getComplete(reportId))
                .thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> reportTerminator.delete(reportId)
        );

        assertTrue(exception
                .getMessage()
                .contains("not found")
        );

        verify(repository, never()).deleteComplete(any());
    }

    @Test
    @DisplayName("Deleting all complete reports")
    void shouldDelegateActionToTheRepository() {
        // When
        reportTerminator.deleteAll();

        // Then
        verify(repository).deleteAllComplete();
    }
}
