package com.report.application.converter;

import com.report.application.domain.type.ReportStatus;
import com.report.application.dto.FilmCharacterDetails;
import com.report.application.dto.ReportDetails;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportEntityToDetailsConverterTest {
    private static final String CHARACTER_PHRASE = "Star Wars";
    private static final String PLANET_NAME = "Leia";

    @Mock
    private FilmCharacterDetails filmCharacterDetails;

    @Mock
    private FilmCharacter filmCharacterEntity;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ReportEntityToDetailsConverter converter;

    private UUID reportId;
    private Report report;

    @BeforeEach
    void init() {
        reportId = UUID.randomUUID();

        report = new Report(
                reportId,
                CHARACTER_PHRASE,
                PLANET_NAME,
                ReportStatus.COMPLETE,
                Collections.emptyList()
        );
    }

    @Test
    @DisplayName("Converting null")
    void shouldReturnNull() {
        // When
        ReportDetails result = converter.convert((Report) null);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Converting entity without FilmCharacters")
    void shouldReturnDetailsFilledByMostlyTheSameRawData() {
        // When
        ReportDetails result = converter.convert(report);

        // Then
        assertEquals(reportId, result.getReportId());
        assertEquals(CHARACTER_PHRASE, result.getCharacterPhrase());
        assertEquals(PLANET_NAME, result.getPlanetName());
        assertTrue(result.getFilmCharacters()
                .isEmpty());

        verify(modelMapper, never()).map(
                any(), eq(FilmCharacterDetails.class)
        );
    }

    @Test
    @DisplayName("Converting entity with FilmCharacters")
    void shouldReturnDetailsFilledByMostlyTheSameRawDataWithFilmCharacterDetails() {
        // Given
        report.setFilmCharacters(
                Collections.singletonList(filmCharacterEntity)
        );

        when(modelMapper.map(any(), eq(FilmCharacterDetails.class)))
                .thenReturn(filmCharacterDetails);

        // When
        ReportDetails result = converter.convert(report);

        // Then
        assertEquals(reportId, result.getReportId());
        assertEquals(CHARACTER_PHRASE, result.getCharacterPhrase());
        assertEquals(PLANET_NAME, result.getPlanetName());
        assertTrue(result.getFilmCharacters()
                .contains(filmCharacterDetails));

        verify(modelMapper).map(
                eq(filmCharacterEntity), eq(FilmCharacterDetails.class)
        );
    }
}
