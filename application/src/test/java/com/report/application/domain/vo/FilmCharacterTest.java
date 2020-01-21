package com.report.application.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FilmCharacterTest {
    @Mock
    private FilmId filmId;
    @Mock
    private FilmName filmName;
    @Mock
    private CharacterId characterId;
    @Mock
    private CharacterName characterName;
    @Mock
    private PlanetId planetId;
    @Mock
    private PlanetName planetName;

    @Test
    @DisplayName("Creating with null values")
    void shouldNotAcceptNullValues() {
        // When & Then
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> new FilmCharacter(null, null, null, null, null, null)
        );

        assertTrue(exception
                .getMessage()
                .contains("is marked non-null but is null")
        );
    }

    @Test
    @DisplayName("Creating with valid data")
    void shouldAcceptValidData() {
        // When & Then
        assertDoesNotThrow(
            () -> new FilmCharacter(filmId, filmName, characterId, characterName, planetId, planetName)
        );
    }

    @Test
    @DisplayName("Getting access to Raw values")
    void shouldGiveSameRawValuesAsProvided() {
        // When
        FilmCharacter filmCharacter = new FilmCharacter(filmId, filmName, characterId, characterName, planetId, planetName);

        // Then
        assertEquals(filmId, filmCharacter.getFilmId());
        assertEquals(filmName, filmCharacter.getFilmName());
        assertEquals(characterId, filmCharacter.getCharacterId());
        assertEquals(characterName, filmCharacter.getCharacterName());
        assertEquals(planetId, filmCharacter.getPlanetId());
        assertEquals(planetName, filmCharacter.getPlanetName());
    }
}
