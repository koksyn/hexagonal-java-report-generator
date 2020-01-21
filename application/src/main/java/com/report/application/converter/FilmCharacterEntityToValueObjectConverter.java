package com.report.application.converter;

import com.google.common.primitives.UnsignedLong;
import com.report.application.domain.vo.*;
import com.report.application.entity.FilmCharacter;
import org.modelmapper.AbstractConverter;

public class FilmCharacterEntityToValueObjectConverter extends AbstractConverter<FilmCharacter, com.report.application.domain.vo.FilmCharacter> {
    @Override
    protected com.report.application.domain.vo.FilmCharacter convert(FilmCharacter entity) {
        if (entity == null) {
            return null;
        }

        UnsignedLong filmId = UnsignedLong
                .valueOf(entity.getFilmId());

        UnsignedLong planetId = UnsignedLong
                .valueOf(entity.getPlanetId());

        UnsignedLong characterId = UnsignedLong
                .valueOf(entity.getCharacterId());

        return new com.report.application.domain.vo.FilmCharacter(
                new FilmId(filmId),
                new FilmName(entity.getFilmName()),
                new CharacterId(characterId),
                new CharacterName(entity.getCharacterName()),
                new PlanetId(planetId),
                new PlanetName(entity.getPlanetName())
        );
    }
}