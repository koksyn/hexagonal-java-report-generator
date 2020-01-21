package com.report.adapter.persistence.converter;

import com.report.application.entity.FilmCharacter;
import com.google.common.primitives.UnsignedLong;
import com.report.application.domain.vo.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FilmCharacterValueObjectToEntityConverterTest {
    private static final Long FILM_ID = 1L;
    private static final String FILM_NAME = "Star Wars";
    private static final Long CHARACTER_ID = 2L;
    private static final String CHARACTER_NAME = "Leia";
    private static final Long PLANET_ID = 3L;
    private static final String PLANET_NAME = "Alderaan";

    private static com.report.application.domain.vo.FilmCharacter filmCharacter;
    private static FilmCharacterValueObjectToEntityConverter converter;

    @BeforeAll
    static void init() {
        filmCharacter = new com.report.application.domain.vo.FilmCharacter(
                new FilmId(UnsignedLong.valueOf(FILM_ID)),
                new FilmName(FILM_NAME),
                new CharacterId(UnsignedLong.valueOf(CHARACTER_ID)),
                new CharacterName(CHARACTER_NAME),
                new PlanetId(UnsignedLong.valueOf(PLANET_ID)),
                new PlanetName(PLANET_NAME)
        );

        converter = new FilmCharacterValueObjectToEntityConverter();
    }

    @Test
    @DisplayName("Converting null")
    void shouldReturnNull() {
        // When
        FilmCharacter result = converter.convert(
                (com.report.application.domain.vo.FilmCharacter) null
        );

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Converting value object")
    void shouldReturnEntityFilledByTheSameRawData() {
        // When
        FilmCharacter result = converter.convert(filmCharacter);

        // Then
        assertEquals(FILM_ID, result.getFilmId());
        assertEquals(FILM_NAME, result.getFilmName());
        assertEquals(CHARACTER_ID, result.getCharacterId());
        assertEquals(CHARACTER_NAME, result.getCharacterName());
        assertEquals(PLANET_ID, result.getPlanetId());
        assertEquals(PLANET_NAME, result.getPlanetName());
    }
}
