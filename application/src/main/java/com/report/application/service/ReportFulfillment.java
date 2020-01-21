package com.report.application.service;

import com.report.application.domain.Report;
import com.report.application.domain.snapshot.ReportSnapshot;
import com.report.application.domain.vo.CharacterPhrase;
import com.report.application.domain.vo.FilmCharacter;
import com.report.application.domain.vo.PlanetName;
import com.report.application.port.driven.SwapiRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@RequiredArgsConstructor
public class ReportFulfillment {
    private final SwapiRepository swapiRepository;
    private final ModelMapper modelMapper;

    void fillWithFilmCharacters(@NonNull Report report) {
        ReportSnapshot snapshot = report.toSnapshot();

        fillWithFilmCharactersThatMeetTheCriteria(
                report,
                snapshot.getPlanetName(),
                snapshot.getCharacterPhrase()
        );

        report.markComplete();
    }

    private void fillWithFilmCharactersThatMeetTheCriteria(Report report,
                                                           PlanetName planetName,
                                                           CharacterPhrase characterPhrase) {
        swapiRepository.getFilmCharacterRecordsThatMeetTheCriteria(planetName, characterPhrase)
                .stream()
                .map(filmCharacterRecord -> modelMapper.map(filmCharacterRecord, FilmCharacter.class))
                .forEach(report::addFilmCharacter);
    }
}