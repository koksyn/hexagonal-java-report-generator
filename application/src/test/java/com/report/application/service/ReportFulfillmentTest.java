package com.report.application.service;

import com.report.application.domain.Report;
import com.report.application.domain.snapshot.ReportSnapshot;
import com.report.application.domain.vo.CharacterPhrase;
import com.report.application.domain.vo.FilmCharacter;
import com.report.application.domain.vo.PlanetName;
import com.report.application.dto.FilmCharacterRecord;
import com.report.application.port.driven.SwapiRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportFulfillmentTest {
    @Mock
    private Report report;
    @Mock
    private ReportSnapshot snapshot;
    @Mock
    private PlanetName planetName;
    @Mock
    private CharacterPhrase characterPhrase;
    @Mock
    private FilmCharacter filmCharacter;
    @Mock
    private FilmCharacterRecord filmCharacterRecord;

    @Mock
    private SwapiRepository swapiRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ReportFulfillment reportFulfillment;

    @Test
    @DisplayName("Filling null with FilmCharacters")
    void shouldNotAcceptNullValue() {
        // When & Then
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> reportFulfillment.fillWithFilmCharacters(null)
        );

        assertTrue(exception
                .getMessage()
                .contains("is marked non-null but is null")
        );
    }

    @Test
    @DisplayName("Filling Report with FilmCharacters, when they don't meet the criteria")
    void shouldMarkReportAsCompleteWithoutAddingFilmCharacters() {
        // Given
        prepareSnapshotStubbing();

        when(swapiRepository.getFilmCharacterRecordsThatMeetTheCriteria(planetName, characterPhrase))
                .thenReturn(Collections.emptyList());

        // When
        reportFulfillment.fillWithFilmCharacters(report);

        // Then
        verify(report, never())
                .addFilmCharacter(any(FilmCharacter.class));
        verify(report).markComplete();
    }

    @Test
    @DisplayName("Filling Report with FilmCharacters, when they do meet the criteria")
    void shouldMarkReportAsCompleteWithAddingFilmCharacter() {
        // Given
        prepareSnapshotStubbing();

        when(swapiRepository.getFilmCharacterRecordsThatMeetTheCriteria(planetName, characterPhrase))
                .thenReturn(Collections.singletonList(filmCharacterRecord));

        when(modelMapper.map(filmCharacterRecord, FilmCharacter.class))
                .thenReturn(filmCharacter);

        // When
        reportFulfillment.fillWithFilmCharacters(report);

        // Then
        verify(report).addFilmCharacter(filmCharacter);
        verify(report).markComplete();
    }

    private void prepareSnapshotStubbing() {
        when(report.toSnapshot())
                .thenReturn(snapshot);

        when(snapshot.getPlanetName())
                .thenReturn(planetName);

        when(snapshot.getCharacterPhrase())
                .thenReturn(characterPhrase);
    }
}
