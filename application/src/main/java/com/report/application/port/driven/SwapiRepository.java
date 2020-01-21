package com.report.application.port.driven;

import com.report.application.domain.vo.CharacterPhrase;
import com.report.application.domain.vo.PlanetName;
import com.report.application.dto.FilmCharacterRecord;

import java.util.List;

public interface SwapiRepository {
    void deleteSwapiData();

    List<FilmCharacterRecord> getFilmCharacterRecordsThatMeetTheCriteria(PlanetName planetName, CharacterPhrase characterPhrase);
}