package com.report.application.service;

import com.report.application.domain.Report;
import com.report.application.port.driven.ReportRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportProcessorTest {
    @Mock
    private Report report;
    @Mock
    private ReportFulfillment reportFulfillment;
    @Mock
    private ReportRepository reportRepository;
    @Mock
    private SwapiCache swapiCache;

    @InjectMocks
    private ReportProcessor reportProcessor;

    @Test
    @DisplayName("Processing incomplete reports, when they does not exist")
    void shouldAskForAnyIncompleteAndThenDoNothing() {
        // Given
        when(reportRepository.anyIncomplete())
                .thenReturn(false);

        // When
        reportProcessor.tryProcessIncompleteReports();

        // Then
        verify(swapiCache, never())
                .refresh();
        verify(reportFulfillment, never())
                .fillWithFilmCharacters(any(Report.class));
        verify(reportRepository, never())
                .saveAll(anyList());
    }

    @Test
    @DisplayName("Processing incomplete reports, when they does exist.")
    void shouldProcessThemAndSaveAll() {
        // Given
        when(reportRepository.anyIncomplete())
                .thenReturn(true);

        List<Report> reports = Collections.singletonList(report);

        when(reportRepository.getAllIncomplete())
                .thenReturn(reports);

        // When
        reportProcessor.tryProcessIncompleteReports();

        // Then
        verify(swapiCache).refresh();
        verify(reportFulfillment).fillWithFilmCharacters(report);
        verify(reportRepository).saveAll(reports);
    }
}
