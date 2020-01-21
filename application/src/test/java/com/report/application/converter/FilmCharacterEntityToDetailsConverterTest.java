package com.report.application.converter;

import com.report.application.dto.FilmCharacterDetails;
import com.report.application.entity.FilmCharacter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FilmCharacterEntityToDetailsConverterTest {
    private static final Long FILM_ID = 1L;
    private static final Long PLANET_ID = 2L;
    private static final Long CHARACTER_ID = 3L;
    private static final String FILM_NAME = "Space Odyssey";
    private static final String PLANET_NAME = "Mars";
    private static final String CHARACTER_NAME = "Elon Musk";

    private FilmCharacterEntityToDetailsConverter converter;
    private FilmCharacter filmCharacter;

    @BeforeEach
    void init() {
        filmCharacter = new FilmCharacter(
                UUID.randomUUID(),
                null,
                FILM_ID,
                FILM_NAME,
                CHARACTER_ID,
                CHARACTER_NAME,
                PLANET_ID,
                PLANET_NAME
        );

        converter = new FilmCharacterEntityToDetailsConverter();
    }

    @Test
    @DisplayName("Converting null")
    void shouldReturnNull() {
        // When
        FilmCharacterDetails result = converter.convert((FilmCharacter) null);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Converting entity")
    void shouldReturnDetailsFilledByMostlyTheSameRawData() {
        // When
        FilmCharacterDetails result = converter.convert(filmCharacter);

        // Then
        assertEquals(FILM_ID.toString(), result.getFilmId());
        assertEquals(FILM_NAME, result.getFilmName());
        assertEquals(CHARACTER_ID.toString(), result.getCharacterId());
        assertEquals(CHARACTER_NAME, result.getCharacterName());
        assertEquals(PLANET_ID.toString(), result.getPlanetId());
        assertEquals(PLANET_NAME, result.getPlanetName());
    }
}
