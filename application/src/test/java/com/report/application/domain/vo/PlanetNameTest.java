package com.report.application.domain.vo;

import com.report.common.unit.vo.NonBlankNameTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlanetNameTest extends NonBlankNameTest<PlanetName> {
    private static final String PLANET_NAME = "Mars";
    private static PlanetName planetName;

    @Override
    protected Class<PlanetName> getClassOfTheInstanceBeingCreated() {
        return PlanetName.class;
    }

    @BeforeAll
    static void init() {
        planetName = new PlanetName(PLANET_NAME);
    }

    @Test
    @DisplayName("Comparison with null value")
    void shouldNotCompareNullValue() {
        // When & Then
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> planetName.differ(null)
        );

        assertTrue(exception
                .getMessage()
                .contains("is marked non-null but is null")
        );
    }

    @Test
    @DisplayName("Comparison with equal value")
    void shouldReturnFalse() {
        // Given
        PlanetName samePlanetName = new PlanetName(PLANET_NAME);

        // When
        boolean result = planetName.differ(samePlanetName);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Comparison with differ value")
    void shouldReturnTrue() {
        // Given
        PlanetName differPlanetName = new PlanetName("test");

        // When
        boolean result = planetName.differ(differPlanetName);

        // Then
        assertTrue(result);
    }
}
