package com.report.application.converter;

import com.report.application.entity.FilmCharacter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FilmCharacterEntityToValueObjectConverterTest {
    private static final Long FILM_ID = 1L;
    private static final Long PLANET_ID = 2L;
    private static final Long CHARACTER_ID = 3L;
    private static final String FILM_NAME = "Space Odyssey";
    private static final String PLANET_NAME = "Mars";
    private static final String CHARACTER_NAME = "Elon Musk";

    private FilmCharacterEntityToValueObjectConverter converter;
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

        converter = new FilmCharacterEntityToValueObjectConverter();
    }

    @Test
    @DisplayName("Converting null")
    void shouldReturnNull() {
        // When
        com.report.application.domain.vo.FilmCharacter result = converter.convert((FilmCharacter) null);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Converting entity")
    void shouldReturnValueObjectFilledByMostlyTheSameRawData() {
        // When
        com.report.application.domain.vo.FilmCharacter result = converter.convert(filmCharacter);

        // Then
        assertEquals(FILM_ID, result.getFilmId()
                .getRaw()
                .longValue());
        assertEquals(FILM_NAME, result.getFilmName()
                .getRaw());
        assertEquals(CHARACTER_ID, result.getCharacterId()
                .getRaw()
                .longValue());
        assertEquals(CHARACTER_NAME, result.getCharacterName()
                .getRaw());
        assertEquals(PLANET_ID, result.getPlanetId()
                .getRaw()
                .longValue());
        assertEquals(PLANET_NAME, result.getPlanetName()
                .getRaw());
    }
}
