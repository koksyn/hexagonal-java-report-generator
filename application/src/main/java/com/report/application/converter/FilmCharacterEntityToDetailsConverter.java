package com.report.application.converter;

import com.report.application.dto.FilmCharacterDetails;
import com.report.application.entity.FilmCharacter;
import org.modelmapper.AbstractConverter;

public class FilmCharacterEntityToDetailsConverter extends AbstractConverter<FilmCharacter, FilmCharacterDetails> {
    @Override
    protected FilmCharacterDetails convert(FilmCharacter entity) {
        if (entity == null) {
            return null;
        }

        return new FilmCharacterDetails(
                entity.getFilmId()
                        .toString(),
                entity.getFilmName(),
                entity.getCharacterId()
                        .toString(),
                entity.getCharacterName(),
                entity.getPlanetId()
                        .toString(),
                entity.getPlanetName()
        );
    }
}