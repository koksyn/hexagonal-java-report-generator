package com.report.adapter.persistence.converter;

import com.report.application.domain.snapshot.ReportSnapshot;
import com.report.application.domain.type.ReportStatus;
import com.report.application.domain.vo.CharacterPhrase;
import com.report.application.domain.vo.FilmCharacterList;
import com.report.application.domain.vo.PlanetName;
import com.report.application.domain.vo.ReportId;
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
class ReportSnapshotToEntityConverterTest {
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
    private ReportSnapshotToEntityConverter converter;

    @BeforeEach
    void init() {
        reportId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Converting null")
    void shouldReturnNull() {
        // When
        Report result = converter.convert((ReportSnapshot) null);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Converting snapshot without FilmCharacters")
    void shouldReturnEntityFilledByMostlyTheSameRawData() {
        // Given
        ReportSnapshot snapshot = buildFrom(new FilmCharacterList());

        // When
        Report result = converter.convert(snapshot);

        // Then
        assertEquals(reportId, result.getReportId());
        assertEquals(CHARACTER_PHRASE, result.getCharacterPhrase());
        assertEquals(PLANET_NAME, result.getPlanetName());
        assertEquals(ReportStatus.COMPLETE, result.getStatus());
        assertTrue(result.getFilmCharacters()
                .isEmpty());

        verify(modelMapper, never()).map(
                any(), eq(FilmCharacter.class)
        );
    }

    @Test
    @DisplayName("Converting snapshot with FilmCharacters")
    void shouldReturnEntityFilledByMostlyTheSameRawDataWithFilmCharacters() {
        // Given
        List<com.report.application.domain.vo.FilmCharacter> filmCharacters =
                Collections.singletonList(filmCharacter);

        FilmCharacterList filmCharacterList = new FilmCharacterList(filmCharacters);

        ReportSnapshot snapshot = buildFrom(filmCharacterList);

        when(modelMapper.map(any(), eq(FilmCharacter.class)))
                .thenReturn(filmCharacterEntity);

        // When
        Report result = converter.convert(snapshot);

        // Then
        assertEquals(reportId, result.getReportId());
        assertEquals(CHARACTER_PHRASE, result.getCharacterPhrase());
        assertEquals(PLANET_NAME, result.getPlanetName());
        assertEquals(ReportStatus.COMPLETE, result.getStatus());
        assertTrue(result.getFilmCharacters()
                .contains(filmCharacterEntity));

        verify(modelMapper).map(
                eq(filmCharacter), eq(FilmCharacter.class)
        );

        verify(filmCharacterEntity).setReport(eq(result));
    }

    private ReportSnapshot buildFrom(FilmCharacterList filmCharacterList) {
        return new ReportSnapshot(
                new ReportId(reportId),
                new CharacterPhrase(CHARACTER_PHRASE),
                new PlanetName(PLANET_NAME),
                filmCharacterList,
                new com.report.application.domain.vo.ReportStatus(ReportStatus.COMPLETE)
        );
    }
}
