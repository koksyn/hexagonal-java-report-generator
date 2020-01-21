package com.report.application.service;

import com.report.application.domain.vo.ReportId;
import com.report.application.dto.ReportDetails;
import com.report.application.entity.Report;
import com.report.application.port.driven.ReportRepository;
import com.report.common.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportProviderTest {
    @Mock
    private ReportId reportId;
    @Mock
    private Report reportEntity;
    @Mock
    private ReportDetails reportDetails;
    @Mock
    private ReportRepository repository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ReportProvider reportProvider;

    @Test
    @DisplayName("Getting complete report details, when ReportId is null")
    void shouldNotAcceptNull() {
        // When & Then
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> reportProvider.get(null)
        );

        assertTrue(exception
                .getMessage()
                .contains("is marked non-null but is null")
        );
    }

    @Test
    @DisplayName("Getting complete report details, when it does exist")
    void shouldReturnReportDetails() {
        // Given
        when(repository.getComplete(reportId))
                .thenReturn(Optional.of(reportEntity));

        when(modelMapper.map(reportEntity, ReportDetails.class))
                .thenReturn(reportDetails);

        // When
        ReportDetails result = reportProvider.get(reportId);

        // Then
        assertEquals(reportDetails, result);
    }

    @Test
    @DisplayName("Getting complete report details, when it does not exist")
    void shouldThrowException() {
        // Given
        when(repository.getComplete(reportId))
                .thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> reportProvider.get(reportId)
        );

        assertTrue(exception
                .getMessage()
                .contains("not found")
        );
    }

    @Test
    @DisplayName("Getting all complete reports, when they does exist")
    void shouldReturnListOfReportDetails() {
        // Given
        when(repository.getAllComplete())
                .thenReturn(Collections.singletonList(reportEntity));

        when(modelMapper.map(reportEntity, ReportDetails.class))
                .thenReturn(reportDetails);

        // When
        List<ReportDetails> results = reportProvider.getAll();

        // Then
        assertNotNull(results);
        assertTrue(results.contains(reportDetails));
    }

    @Test
    @DisplayName("Getting all complete reports, when they does not exist")
    void shouldReturnEmptyList() {
        // Given
        when(repository.getAllComplete())
                .thenReturn(Collections.emptyList());

        // When
        List<ReportDetails> results = reportProvider.getAll();

        // Then
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }
}
