package com.report.adapter.persistence.repository;

import com.report.application.domain.vo.CharacterPhrase;
import com.report.application.domain.vo.PlanetName;
import com.report.application.dto.FilmCharacterRecord;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SwapiRepository implements com.report.application.port.driven.SwapiRepository {
    private final SwapiNativeRepository swapiNativeRepository;

    @Override
    public void deleteSwapiData() {
        swapiNativeRepository.cleanSwapiTables();
    }

    @Override
    public List<FilmCharacterRecord> getFilmCharacterRecordsThatMeetTheCriteria(
            @NonNull PlanetName planetName,
            @NonNull CharacterPhrase characterPhrase
    ) {
        return swapiNativeRepository.findAllByPlanetNameAndCharacterNameThatContainsPhrase(
                planetName.getRaw(),
                characterPhrase.getRaw()
        );
    }
}
