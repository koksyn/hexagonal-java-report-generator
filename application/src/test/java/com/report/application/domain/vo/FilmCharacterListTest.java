package com.report.application.domain.vo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class FilmCharacterListTest {
    @Mock
    private FilmCharacter filmCharacter;

    private List<FilmCharacter> filmCharacters;
    private FilmCharacterList filmCharacterList;

    @BeforeEach
    void init() {
        filmCharacters = new ArrayList<>(Arrays.asList(filmCharacter));
        filmCharacterList = new FilmCharacterList(filmCharacters);
    }

    @Test
    @DisplayName("Getting access to Raw value for instance created by default constructor")
    void shouldGiveNonNullList() {
        // Given
        FilmCharacterList filmCharacterList = new FilmCharacterList();

        // When
        List<FilmCharacter> filmCharacters = filmCharacterList.getRaw();

        // Then
        assertNotNull(filmCharacters);
    }

    @Test
    @DisplayName("Creating with null value")
    void shouldNotAcceptNullValue() {
        // When & Then
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new FilmCharacterList(null)
        );

        assertEquals("raw is marked non-null but is null", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("validLists")
    @DisplayName("Creating with valid lists")
    void shouldAcceptValidLists(List<FilmCharacter> list) {
        // When & Then
        assertDoesNotThrow(
                () -> new FilmCharacterList(list)
        );
    }

    private static Stream<List<FilmCharacter>> validLists() {
        FilmCharacter filmCharacterMock = mock(FilmCharacter.class);

        return Stream.of(
                Collections.emptyList(),
                Collections.singletonList(filmCharacterMock),
                Collections.nCopies(3, filmCharacterMock)
        );
    }

    @Test
    @DisplayName("Getting access to Raw value")
    void shouldGiveSameRawValueAsProvided() {
        // When
        List<FilmCharacter> results = filmCharacterList.getRaw();

        // Then
        assertEquals(1, results.size());
        assertTrue(results.contains(filmCharacter));
    }

    @Test
    @DisplayName("Adding null value")
    void shouldNotAddNullValue() {
        // When & Then
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> filmCharacterList.add(null)
        );

        assertTrue(exception
                .getMessage()
                .contains("is marked non-null but is null")
        );
    }

    @Test
    @DisplayName("Adding FilmCharacter")
    void shouldAddToTheList() {
        // Given
        filmCharacterList = new FilmCharacterList();

        // When
        filmCharacterList.add(filmCharacter);

        // Then
        List<FilmCharacter> results = filmCharacterList.getRaw();
        assertTrue(results.contains(filmCharacter));
    }

    @Test
    @DisplayName("Clearing list")
    void shouldMakeListEmpty() {
        // When
        filmCharacterList.clear();

        // Then
        List<FilmCharacter> results = filmCharacterList.getRaw();
        assertFalse(results.contains(filmCharacter));
    }
}
