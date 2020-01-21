package com.report.application.service;

import com.report.application.domain.snapshot.ReportSnapshot;
import com.report.application.domain.type.ReportStatus;
import com.report.application.domain.vo.*;
import com.report.application.entity.Report;
import com.report.application.port.driven.ReportRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportCreatorTest {
    @Mock
    private FilmCharacter filmCharacter;
    @Mock
    private ReportId reportId;
    @Mock
    private CharacterPhrase characterPhrase;
    @Mock
    private PlanetName planetName;
    @Mock
    private Report reportEntity;
    @Mock
    private ReportRepository repository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ReportCreator reportCreator;

    @Captor
    private ArgumentCaptor<com.report.application.domain.Report> captor;

    @Test
    @DisplayName("Creating or replacing with null arguments")
    void shouldNotAcceptNullValues() {
        // When & Then
        assertAll(
                () -> assertThrows(NullPointerException.class,
                        () -> reportCreator.createOrReplace(null, null, null)),
                () -> assertThrows(NullPointerException.class,
                        () -> reportCreator.createOrReplace(reportId, null, null)),
                () -> assertThrows(NullPointerException.class,
                        () -> reportCreator.createOrReplace(null, characterPhrase, null)),
                () -> assertThrows(NullPointerException.class,
                        () -> reportCreator.createOrReplace(null, null, planetName)),
                () -> assertThrows(NullPointerException.class,
                        () -> reportCreator.createOrReplace(reportId, characterPhrase, null)),
                () -> assertThrows(NullPointerException.class,
                        () -> reportCreator.createOrReplace(reportId, null, planetName)),
                () -> assertThrows(NullPointerException.class,
                        () -> reportCreator.createOrReplace(null, characterPhrase, planetName))
        );
    }

    @Test
    @DisplayName("Creating or replacing, when it does exist")
    void shouldPrepareAndSaveLoadedReport() {
        // Given
        List<FilmCharacter> filmCharacterList = new LinkedList<>();
        filmCharacterList.add(filmCharacter);

        FilmCharacterList filmCharacters = new FilmCharacterList(filmCharacterList);

        ReportSnapshot snapshot = new ReportSnapshot(
                reportId,
                characterPhrase,
                planetName,
                filmCharacters,
                new com.report.application.domain.vo.ReportStatus(ReportStatus.COMPLETE)
        );

        when(repository.get(reportId))
                .thenReturn(Optional.of(reportEntity));

        when(modelMapper.map(reportEntity, ReportSnapshot.class))
                .thenReturn(snapshot);

        // When
        reportCreator.createOrReplace(reportId, characterPhrase, planetName);

        // Then
        verify(repository).save(captor.capture());
        com.report.application.domain.Report report = captor.getValue();

        assertNotNull(report);
        ReportSnapshot resultSnapshot = report.toSnapshot();

        assertEquals(reportId, resultSnapshot.getReportId());
        assertEquals(planetName, resultSnapshot.getPlanetName());
        assertEquals(characterPhrase, resultSnapshot.getCharacterPhrase());
        assertEquals(ReportStatus.INCOMPLETE, resultSnapshot.getReportStatus()
                .getRaw());
        assertTrue(resultSnapshot.getFilmCharacters()
                .getRaw()
                .isEmpty());
    }

    @Test
    @DisplayName("Creating or replacing, when it does not exist")
    void shouldPrepareAndSaveCreatedReport() {
        // Given
        when(repository.get(reportId))
                .thenReturn(Optional.empty());

        // When
        reportCreator.createOrReplace(reportId, characterPhrase, planetName);

        // Then
        verify(repository).save(captor.capture());
        com.report.application.domain.Report report = captor.getValue();

        assertNotNull(report);
        ReportSnapshot snapshot = report.toSnapshot();

        assertEquals(reportId, snapshot.getReportId());
        assertEquals(planetName, snapshot.getPlanetName());
        assertEquals(characterPhrase, snapshot.getCharacterPhrase());
        assertEquals(ReportStatus.INCOMPLETE, snapshot.getReportStatus()
                .getRaw());
        assertTrue(snapshot.getFilmCharacters()
                .getRaw()
                .isEmpty());
    }
}
