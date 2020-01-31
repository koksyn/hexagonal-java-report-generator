package com.report.application.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilmCharacterListTest {
    @Mock
    private FilmCharacter filmCharacter;

    @Mock
    private List<FilmCharacter> filmCharacters;

    @InjectMocks
    private FilmCharacterList filmCharacterList;

    @Test
    @DisplayName("Getting access to Raw value for instance created by default constructor")
    void shouldGiveNonNullLinkedList() {
        // Given
        FilmCharacterList filmCharacterList = new FilmCharacterList();

        // When
        List<FilmCharacter> filmCharacters = filmCharacterList.getRaw();

        // Then
        assertNotNull(filmCharacters);
        assertTrue(filmCharacters instanceof LinkedList);
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
        assertEquals(filmCharacters, results);
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
        // When
        filmCharacterList.add(filmCharacter);

        // Then
        verify(filmCharacters).add(eq(filmCharacter));
    }

    @Test
    @DisplayName("Clearing list")
    void shouldDoNothing() {
        // When
        filmCharacterList.clear();

        // Then
        verify(filmCharacters).clear();
    }
}
