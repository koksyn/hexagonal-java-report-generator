package com.report.application.converter;

import com.google.common.primitives.UnsignedLong;
import com.report.application.domain.vo.*;
import com.report.application.dto.FilmCharacterRecord;
import org.modelmapper.AbstractConverter;

public class FilmCharacterRecordToValueObjectConverter extends AbstractConverter<FilmCharacterRecord, FilmCharacter> {
    @Override
    protected FilmCharacter convert(FilmCharacterRecord record) {
        if(record == null) {
            return null;
        }

        UnsignedLong filmId = UnsignedLong
                .valueOf(record.getFilmId());

        UnsignedLong characterId = UnsignedLong
                .valueOf(record.getCharacterId());

        UnsignedLong planetId = UnsignedLong
                .valueOf(record.getPlanetId());

        return new FilmCharacter(
                new FilmId(filmId),
                new FilmName(
                        record.getFilmName()
                ),
                new CharacterId(characterId),
                new CharacterName(
                        record.getCharacterName()
                ),
                new PlanetId(planetId),
                new PlanetName(
                        record.getPlanetName()
                )
        );
    }
}