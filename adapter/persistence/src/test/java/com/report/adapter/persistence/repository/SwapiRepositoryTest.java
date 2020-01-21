package com.report.adapter.persistence.repository;

import com.report.application.domain.vo.CharacterPhrase;
import com.report.application.domain.vo.PlanetName;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SwapiRepositoryTest {
    private static final String PLANET_NAME = "Earth";
    private static final String CHARACTER_PHRASE = "Musk";

    private static PlanetName planetName;
    private static CharacterPhrase characterPhrase;

    @Mock
    private SwapiNativeRepository nativeRepository;

    @InjectMocks
    private SwapiRepository repository;

    @BeforeAll
    static void init() {
        planetName = new PlanetName(PLANET_NAME);
        characterPhrase = new CharacterPhrase(CHARACTER_PHRASE);
    }

    @Test
    @DisplayName("Deleting SWAPI data")
    void shouldCleanSwapiTables() {
        // When
        repository.deleteSwapiData();

        // Then
        verify(nativeRepository).cleanSwapiTables();
    }

    @Test
    @DisplayName("Getting FilmCharacterRecords, when all arguments are not null")
    void shouldFindAllByCriteria() {
        // When
        repository.getFilmCharacterRecordsThatMeetTheCriteria(planetName, characterPhrase);

        // Then
        verify(nativeRepository).findAllByPlanetNameAndCharacterNameThatContainsPhrase(PLANET_NAME, CHARACTER_PHRASE);
    }

    @Test
    @DisplayName("Getting FilmCharacterRecords, when some arguments are null")
    void shouldNotAcceptNullValue() {
        // When & Then
        assertAll(
                () -> assertThrows(NullPointerException.class,
                        () -> repository.getFilmCharacterRecordsThatMeetTheCriteria(null, characterPhrase)
                ),
                () -> assertThrows(NullPointerException.class,
                        () -> repository.getFilmCharacterRecordsThatMeetTheCriteria(planetName, null)
                ),
                () -> assertThrows(NullPointerException.class,
                        () -> repository.getFilmCharacterRecordsThatMeetTheCriteria(null, null)
                )
        );
    }
}
