package com.report.adapter.persistence.converter;

import com.report.application.domain.snapshot.ReportSnapshot;
import com.report.application.entity.FilmCharacter;
import com.report.application.entity.Report;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ReportSnapshotToEntityConverter extends AbstractConverter<ReportSnapshot, Report> {
    private final ModelMapper modelMapper;

    @Override
    protected Report convert(ReportSnapshot snapshot) {
        if (snapshot == null) {
            return null;
        }

        Report report = new Report(
                snapshot.getReportId()
                        .getRaw(),
                snapshot.getCharacterPhrase()
                        .getRaw(),
                snapshot.getPlanetName()
                        .getRaw(),
                snapshot.getReportStatus()
                        .getRaw(),
                null
        );

        List<FilmCharacter> filmCharacters = snapshot.getFilmCharacters()
                .getRaw()
                .stream()
                .map(filmCharacter -> mapToEntity(filmCharacter, report))
                .collect(Collectors.toList());

        report.setFilmCharacters(filmCharacters);

        return report;
    }

    private FilmCharacter mapToEntity(com.report.application.domain.vo.FilmCharacter filmCharacter,
                                      Report report) {
        FilmCharacter entity = modelMapper.map(filmCharacter, FilmCharacter.class);
        entity.setReport(report);

        return entity;
    }
}