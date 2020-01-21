package com.report.adapter.api.controller;

import com.report.adapter.api.dto.QueryCriteria;
import com.report.application.domain.vo.CharacterPhrase;
import com.report.application.domain.vo.PlanetName;
import com.report.application.domain.vo.ReportId;
import com.report.application.dto.ReportDetails;
import com.report.application.port.driver.ReportCreator;
import com.report.application.port.driver.ReportProvider;
import com.report.application.port.driver.ReportTerminator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {
    @Mock
    private ReportDetails reportDetails;
    @Mock
    private ReportCreator reportCreator;
    @Mock
    private ReportProvider reportProvider;
    @Mock
    private ReportTerminator reportTerminator;

    @InjectMocks
    private ReportController reportController;

    @Captor
    private ArgumentCaptor<ReportId> reportIdCaptor;
    @Captor
    private ArgumentCaptor<CharacterPhrase> characterPhraseCaptor;
    @Captor
    private ArgumentCaptor<PlanetName> planetNameCaptor;

    private static UUID rawReportId;

    @BeforeAll
    static void init() {
        rawReportId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Getting report details")
    void shouldPassReportIdToTheProviderAndReturnResult() {
        // Given
        when(reportProvider.get(any(ReportId.class)))
                .thenReturn(reportDetails);

        // When
        ReportDetails result = reportController.get(rawReportId);

        // Then
        assertEquals(reportDetails, result);

        verify(reportProvider).get(reportIdCaptor.capture());
        ReportId reportId = reportIdCaptor.getValue();
        assertNotNull(reportId);
        assertEquals(rawReportId, reportId.getRaw());
    }

    @Test
    @DisplayName("Getting details of all reports")
    void shouldExecuteProviderAndReturnResults() {
        // Given
        List<ReportDetails> reports = Collections.singletonList(reportDetails);

        when(reportProvider.getAll())
                .thenReturn(reports);

        // When
        List<ReportDetails> results = reportController.getAll();

        // Then
        verify(reportProvider).getAll();
        assertEquals(reports, results);
    }

    @Test
    @DisplayName("Creating or replacing report")
    void shouldPassReportIdAndCriteriaToTheCreator() {
        // Given
        CharacterPhrase characterPhrase = mock(CharacterPhrase.class);
        PlanetName planetName = mock(PlanetName.class);
        QueryCriteria queryCriteria = mock(QueryCriteria.class);

        when(queryCriteria.toCharacterPhrase())
                .thenReturn(characterPhrase);

        when(queryCriteria.toPlanetName())
                .thenReturn(planetName);

        // When
        reportController.put(rawReportId, queryCriteria);

        // Then
        verify(reportCreator).createOrReplace(
                reportIdCaptor.capture(),
                characterPhraseCaptor.capture(),
                planetNameCaptor.capture()
        );

        ReportId reportId = reportIdCaptor.getValue();
        assertNotNull(reportId);
        assertEquals(rawReportId, reportId.getRaw());

        CharacterPhrase capturedCharacterPhrase = characterPhraseCaptor.getValue();
        assertNotNull(capturedCharacterPhrase);
        assertEquals(characterPhrase, capturedCharacterPhrase);

        PlanetName capturedPlanetName = planetNameCaptor.getValue();
        assertNotNull(capturedPlanetName);
        assertEquals(planetName, capturedPlanetName);
    }

    @Test
    @DisplayName("Deleting report by id")
    void shouldPassReportIdToTheTerminator() {
        // When
        reportController.delete(rawReportId);

        // Then
        verify(reportTerminator).delete(reportIdCaptor.capture());
        ReportId reportId = reportIdCaptor.getValue();
        assertNotNull(reportId);
        assertEquals(rawReportId, reportId.getRaw());
    }

    @Test
    @DisplayName("Deleting all reports")
    void shouldExecuteTerminator() {
        // When
        reportController.deleteAll();

        // Then
        verify(reportTerminator).deleteAll();
    }
}
