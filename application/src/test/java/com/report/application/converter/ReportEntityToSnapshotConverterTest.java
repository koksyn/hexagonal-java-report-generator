package com.report.application.converter;

import com.report.application.domain.snapshot.ReportSnapshot;
import com.report.application.domain.type.ReportStatus;
import com.report.application.entity.FilmCharacter;
import com.report.application.entity.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportEntityToSnapshotConverterTest {
    private static final String CHARACTER_PHRASE = "Star Wars";
    private static final String PLANET_NAME = "Leia";

    private UUID reportId;

    @Mock
    private com.report.application.domain.vo.FilmCharacter filmCharacter;

    @Mock
    private FilmCharacter filmCharacterEntity;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ReportEntityToSnapshotConverter converter;

    @BeforeEach
    void init() {
        reportId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Converting null")
    void shouldReturnNull() {
        // When
        ReportSnapshot result = converter.convert((Report) null);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Converting entity without FilmCharacters")
    void shouldReturnSnapshotFilledByMostlyTheSameRawData() {
        // Given
        Report report = buildFrom(Collections.emptyList());

        // When
        ReportSnapshot result = converter.convert(report);

        // Then
        assertEquals(reportId, result.getReportId()
                .getRaw());
        assertEquals(CHARACTER_PHRASE, result.getCharacterPhrase()
                .getRaw());
        assertEquals(PLANET_NAME, result.getPlanetName()
                .getRaw());
        assertEquals(ReportStatus.COMPLETE, result.getReportStatus()
                .getRaw());
        assertTrue(result.getFilmCharacters()
                .getRaw()
                .isEmpty());

        verify(modelMapper, never()).map(
                any(), eq(FilmCharacter.class)
        );
    }

    @Test
    @DisplayName("Converting entity with FilmCharacters")
    void shouldReturnSnapshotFilledByMostlyTheSameRawDataWithFilmCharacters() {
        // Given
        List<FilmCharacter> filmCharacters = Collections.singletonList(filmCharacterEntity);

        Report report = buildFrom(filmCharacters);

        when(modelMapper.map(any(), eq(com.report.application.domain.vo.FilmCharacter.class)))
                .thenReturn(filmCharacter);

        // When
        ReportSnapshot result = converter.convert(report);

        // Then
        assertEquals(reportId, result.getReportId()
                .getRaw());
        assertEquals(CHARACTER_PHRASE, result.getCharacterPhrase()
                .getRaw());
        assertEquals(PLANET_NAME, result.getPlanetName()
                .getRaw());
        assertEquals(ReportStatus.COMPLETE, result.getReportStatus()
                .getRaw());
        assertTrue(result.getFilmCharacters()
                .getRaw()
                .contains(filmCharacter));

        verify(modelMapper).map(
                eq(filmCharacterEntity), eq(com.report.application.domain.vo.FilmCharacter.class)
        );
    }

    private Report buildFrom(List<FilmCharacter> filmCharacters) {
        return new Report(
                reportId,
                CHARACTER_PHRASE,
                PLANET_NAME,
                ReportStatus.COMPLETE,
                filmCharacters
        );
    }
}
