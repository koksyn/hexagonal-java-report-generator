package com.report.adapter.api.dto;

import com.report.application.domain.vo.CharacterPhrase;
import com.report.application.domain.vo.PlanetName;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryCriteriaTest {
    private static final String PLANET_NAME = "Alderaan";
    private static final String CHARACTER_PHRASE = "Leia";
    private static QueryCriteria queryCriteria;

    @BeforeAll
    static void init() {
        queryCriteria = new QueryCriteria(CHARACTER_PHRASE, PLANET_NAME);
    }

    @Test
    @DisplayName("Converting to character phrase")
    void shouldReturnCharacterPhraseFilledBySameRawDataAsInQuery() {
        // When
        CharacterPhrase characterPhrase = queryCriteria.toCharacterPhrase();

        // Then
        assertEquals(CHARACTER_PHRASE, characterPhrase.getRaw());
    }

    @Test
    @DisplayName("Converting to planet name")
    void shouldReturnPlanetNameFilledBySameRawDataAsInQuery() {
        // When
        PlanetName planetName = queryCriteria.toPlanetName();

        // Then
        assertEquals(PLANET_NAME, planetName.getRaw());
    }
}
