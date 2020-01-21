package com.report.application.converter;

import com.report.application.domain.snapshot.ReportSnapshot;
import com.report.application.domain.vo.*;
import com.report.application.entity.Report;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ReportEntityToSnapshotConverter extends AbstractConverter<Report, ReportSnapshot> {
    private final ModelMapper modelMapper;

    @Override
    protected ReportSnapshot convert(Report entity) {
        if (entity == null) {
            return null;
        }

        List<FilmCharacter> filmCharacters = entity.getFilmCharacters()
                .stream()
                .map(filmCharacter -> modelMapper.map(filmCharacter, FilmCharacter.class))
                .collect(Collectors.toList());

        return new ReportSnapshot(
                new ReportId(entity.getReportId()),
                new CharacterPhrase(entity.getCharacterPhrase()),
                new PlanetName(entity.getPlanetName()),
                new FilmCharacterList(filmCharacters),
                new ReportStatus(entity.getStatus())
        );
    }
}