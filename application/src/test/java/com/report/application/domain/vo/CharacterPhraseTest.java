package com.report.application.domain.vo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CharacterPhraseTest {
    private static final String CHARACTER_PHRASE = "Leia";
    private static CharacterPhrase characterPhrase;

    @BeforeAll
    static void init() {
        characterPhrase = new CharacterPhrase(CHARACTER_PHRASE);
    }

    @Test
    @DisplayName("Creating with null value")
    void shouldNotAcceptNullValue() {
        // When & Then
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new CharacterPhrase(null)
        );

        assertEquals("raw is marked non-null but is null", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("validValues")
    @DisplayName("Creating with valid values")
    void shouldAcceptValidValues(String raw) {
        // When & Then
        assertDoesNotThrow(
                () -> new CharacterPhrase(raw)
        );
    }

    private static Stream<String> validValues() {
        return Stream.of(
                "",
                "123",
                "3-po",
                " leia "
        );
    }

    @Test
    @DisplayName("Getting access to Raw value")
    void shouldGiveSameRawValueAsProvided() {
        // When
        String rawResult = characterPhrase.getRaw();

        // Then
        assertEquals(CHARACTER_PHRASE, rawResult);
    }
}
