package com.report.application.converter;

import com.report.application.dto.FilmCharacterDetails;
import com.report.application.dto.ReportDetails;
import com.report.application.entity.Report;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ReportEntityToDetailsConverter extends AbstractConverter<Report, ReportDetails> {
    private final ModelMapper modelMapper;

    @Override
    protected ReportDetails convert(Report entity) {
        if (entity == null) {
            return null;
        }

        List<FilmCharacterDetails> filmCharacterDetailsList = entity.getFilmCharacters()
                .stream()
                .map(filmCharacter -> modelMapper.map(filmCharacter, FilmCharacterDetails.class))
                .collect(Collectors.toList());

        return new ReportDetails(
                entity.getReportId(),
                entity.getCharacterPhrase(),
                entity.getPlanetName(),
                filmCharacterDetailsList
        );
    }
}