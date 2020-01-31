package com.report.application.domain;

import com.report.application.domain.snapshot.ReportSnapshot;
import com.report.application.domain.vo.*;
import com.report.common.exception.LogicException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportTest {
    private static ReportId reportId;
    private static CharacterPhrase characterPhrase;
    private static PlanetName planetName;
    private static FilmCharacterList filmCharacters;
    private static ReportStatus reportStatus;

    @Mock
    private FilmCharacter filmCharacter;

    @Captor
    private ArgumentCaptor<FilmCharacter> captor;

    @BeforeAll
    static void init() {
        reportId = new ReportId();
        characterPhrase = new CharacterPhrase("Luck");
        planetName = new PlanetName("Mars");
        filmCharacters = new FilmCharacterList();
        reportStatus = new ReportStatus(com.report.application.domain.type.ReportStatus.INCOMPLETE);
    }

    @Test
    @DisplayName("Creating with null values")
    void shouldNotAcceptNullValuesInConstructor() {
        // When & Then
        assertAll(
                () -> assertThrows(NullPointerException.class, () -> new Report(null, null, null)),
                () -> assertThrows(NullPointerException.class, () -> new Report(reportId, null, null)),
                () -> assertThrows(NullPointerException.class, () -> new Report(null, characterPhrase, null)),
                () -> assertThrows(NullPointerException.class, () -> new Report(reportId, characterPhrase, null)),
                () -> assertThrows(NullPointerException.class, () -> new Report(null, characterPhrase, planetName)),
                () -> assertThrows(NullPointerException.class, () -> new Report(reportId, null, planetName)),
                () -> assertThrows(NullPointerException.class, () -> new Report(null, null, planetName))
        );
    }

    @Test
    @DisplayName("Creating with required VO objects")
    void shouldAcceptNeededValueObjectsInConstructor() {
        // When & Then
        assertDoesNotThrow(
                () -> new Report(reportId, characterPhrase, planetName)
        );
    }

    @Test
    @DisplayName("Creating with public constructor, which should lead to coherent initial state")
    void shouldHaveInitialCoherentStateAfterCreation() {
        // When
        Report report = new Report(reportId, characterPhrase, planetName);
        ReportSnapshot snapshot = report.toSnapshot();

        // Then
        assertEquals(reportId, snapshot.getReportId());
        assertEquals(characterPhrase, snapshot.getCharacterPhrase());
        assertEquals(planetName, snapshot.getPlanetName());
        assertEquals(
                com.report.application.domain.type.ReportStatus.INCOMPLETE,
                snapshot.getReportStatus()
                        .getRaw()
        );
        assertNotNull(snapshot.getFilmCharacters());
        assertTrue(snapshot.getFilmCharacters()
                .getRaw()
                .isEmpty());
    }

    @Test
    @DisplayName("Creating from Snapshot, which is null")
    void shouldNotAcceptNullValueForCreatingInstanceFromSnapshot() {
        // When & Then
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> Report.fromSnapshot(null)
        );

        assertTrue(exception.getMessage()
                .contains("is marked non-null but is null"));
    }

    @Test
    @DisplayName("Creating from Snapshot, which is filled by null values")
    void shouldNotAcceptNullValuesInsideSnapshotWhileCreatingInstance() {
        // When & Then
        assertAll(
                () -> assertThrows(NullPointerException.class, () -> {
                    ReportSnapshot snapshot =
                            new ReportSnapshot(null, characterPhrase, planetName, filmCharacters, reportStatus);
                    Report.fromSnapshot(snapshot);
                }),
                () -> assertThrows(NullPointerException.class, () -> {
                    ReportSnapshot snapshot =
                            new ReportSnapshot(reportId, null, planetName, filmCharacters, reportStatus);
                    Report.fromSnapshot(snapshot);
                }),
                () -> assertThrows(NullPointerException.class, () -> {
                    ReportSnapshot snapshot =
                            new ReportSnapshot(reportId, characterPhrase, null, filmCharacters, reportStatus);
                    Report.fromSnapshot(snapshot);
                }),
                () -> assertThrows(NullPointerException.class, () -> {
                    ReportSnapshot snapshot =
                            new ReportSnapshot(reportId, characterPhrase, planetName, null, reportStatus);
                    Report.fromSnapshot(snapshot);
                }),
                () -> assertThrows(NullPointerException.class, () -> {
                    ReportSnapshot snapshot =
                            new ReportSnapshot(reportId, characterPhrase, planetName, filmCharacters, null);
                    Report.fromSnapshot(snapshot);
                })
        );
    }

    @Test
    @DisplayName("Creating from Snapshot and giving a Snapshot back with the partially same parameters")
    void shouldProduceInstanceFromSnapshotWithPartiallySameParametersAsInSnapshot() {
        // Given
        ReportSnapshot snapshot =
                new ReportSnapshot(reportId, characterPhrase, planetName, filmCharacters, reportStatus);

        // When & Then
        assertDoesNotThrow(
                () -> {
                    Report report = Report.fromSnapshot(snapshot);
                    ReportSnapshot result = report.toSnapshot();

                    assertEquals(reportId, result.getReportId());
                    assertEquals(characterPhrase, result.getCharacterPhrase());
                    assertEquals(planetName, result.getPlanetName());
                    assertEquals(filmCharacters, result.getFilmCharacters());
                    assertEquals(
                            reportStatus.getRaw(),
                            result.getReportStatus()
                                    .getRaw()
                    );
                }
        );
    }

    @Test
    @DisplayName("Preparing for processing, when all arguments are different than inside domain")
    void shouldChangePlanetNameAndCharacterPhraseAndStatusWithCleaningList() {
        // Given
        FilmCharacterList filmCharacterList = mock(FilmCharacterList.class);
        ReportSnapshot snapshot =
                new ReportSnapshot(reportId, characterPhrase, planetName, filmCharacterList, reportStatus);
        Report report = Report.fromSnapshot(snapshot);
        CharacterPhrase anotherPhrase = new CharacterPhrase("another");
        PlanetName anotherName = new PlanetName("another");

        // When
        report.prepareForProcessing(anotherPhrase, anotherName);

        // Then
        snapshot = report.toSnapshot();

        assertEquals(anotherPhrase, snapshot.getCharacterPhrase());
        assertEquals(anotherName, snapshot.getPlanetName());
        assertEquals(
                com.report.application.domain.type.ReportStatus.INCOMPLETE,
                snapshot.getReportStatus()
                        .getRaw()
        );
        verify(filmCharacterList).clear();
    }

    @Test
    @DisplayName("Marking as complete, when report is incomplete")
    void shouldChangeStatus() {
        // Given
        Report report = new Report(reportId, characterPhrase, planetName);

        // When & Then
        assertDoesNotThrow(report::markComplete);

        ReportSnapshot snapshot = report.toSnapshot();

        assertEquals(
                com.report.application.domain.type.ReportStatus.COMPLETE,
                snapshot.getReportStatus()
                    .getRaw()
        );
    }

    @Test
    @DisplayName("Marking as complete, when report is complete already")
    void shouldThrowException() {
        // Given
        ReportStatus completeStatus = new ReportStatus(com.report.application.domain.type.ReportStatus.COMPLETE);
        ReportSnapshot snapshot =
                new ReportSnapshot(reportId, characterPhrase, planetName, filmCharacters, completeStatus);
        Report report = Report.fromSnapshot(snapshot);

        // When & Then
        LogicException exception = assertThrows(LogicException.class, report::markComplete);

        assertTrue(exception.getMessage()
                .contains("report is complete already"));
    }

    @Test
    @DisplayName("Adding FilmCharacter, when it was not added before")
    void shouldAddItToTheList() {
        // Given
        FilmCharacterList filmCharacterList = mock(FilmCharacterList.class);

        ReportSnapshot snapshot =
                new ReportSnapshot(reportId, characterPhrase, planetName, filmCharacterList, reportStatus);
        Report report = Report.fromSnapshot(snapshot);

        // When & Then
        assertDoesNotThrow(() -> {
            report.addFilmCharacter(filmCharacter);
        });

        verify(filmCharacterList).add(captor.capture());
        FilmCharacter captured = captor.getValue();
        assertNotNull(captured);
        assertEquals(filmCharacter, captured);
    }

    @Test
    @DisplayName("Adding FilmCharacter, when it was added before")
    void shouldNotAcceptDuplicates() {
        // Given
        FilmCharacterList filmCharacterList = mock(FilmCharacterList.class);

        ReportSnapshot snapshot =
                new ReportSnapshot(reportId, characterPhrase, planetName, filmCharacterList, reportStatus);
        Report report = Report.fromSnapshot(snapshot);

        when(filmCharacterList.contains(filmCharacter)).thenReturn(true);

        // When & Then
        LogicException exception = assertThrows(
                LogicException.class,
                () -> report.addFilmCharacter(filmCharacter)
        );

        assertTrue(exception.getMessage()
                .contains("Cannot add"));
    }
}
