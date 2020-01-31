package com.report.application.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

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
    @InjectMocks
    private FilmCharacter filmCharacter;

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
        // Then
        assertEquals(filmId, filmCharacter.getFilmId());
        assertEquals(filmName, filmCharacter.getFilmName());
        assertEquals(characterId, filmCharacter.getCharacterId());
        assertEquals(characterName, filmCharacter.getCharacterName());
        assertEquals(planetId, filmCharacter.getPlanetId());
        assertEquals(planetName, filmCharacter.getPlanetName());
    }

    @ParameterizedTest
    @MethodSource("differentObjects")
    @DisplayName("Comparing with different objects")
    void shouldReturnFalse(Object differentObject) {
        // When
        boolean result = filmCharacter.equals(differentObject);

        // Then
        assertFalse(result);
    }

    static Stream<Object> differentObjects() {
        return Stream.of(
                null,
                0,
                1,
                "abcd"
        );
    }

    @Test
    @DisplayName("Comparing with object with different properties inside")
    void shouldAlsoReturnFalse() {
        // Given
        FilmCharacter differentFilmCharacter = new FilmCharacter(
                filmId,
                filmName,
                characterId,
                characterName,
                planetId,
                new PlanetName("sth different")
        );

        // When
        boolean result = filmCharacter.equals(differentFilmCharacter);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Comparing with the same object")
    void shouldReturnTrue() {
        // When
        boolean result = filmCharacter.equals(filmCharacter);

        // Then
        assertTrue(result);
    }
}
