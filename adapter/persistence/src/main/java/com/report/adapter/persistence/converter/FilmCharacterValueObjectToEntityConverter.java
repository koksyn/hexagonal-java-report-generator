package com.report.adapter.persistence.converter;

import com.report.application.entity.FilmCharacter;
import org.modelmapper.AbstractConverter;

public class FilmCharacterValueObjectToEntityConverter extends AbstractConverter<com.report.application.domain.vo.FilmCharacter, FilmCharacter> {
    @Override
    protected FilmCharacter convert(com.report.application.domain.vo.FilmCharacter filmCharacter) {
        if (filmCharacter == null) {
            return null;
        }

        Long filmId = filmCharacter.getFilmId()
                        .getRaw()
                        .longValue();

        String filmName = filmCharacter.getFilmName()
                        .getRaw();

        Long characterId = filmCharacter.getCharacterId()
                        .getRaw()
                        .longValue();

        String characterName = filmCharacter.getCharacterName()
                        .getRaw();

        Long planetId = filmCharacter.getPlanetId()
                        .getRaw()
                        .longValue();

        String planetName = filmCharacter.getPlanetName()
                        .getRaw();

        FilmCharacter entity = new FilmCharacter();

        entity.setFilmId(filmId);
        entity.setFilmName(filmName);
        entity.setCharacterId(characterId);
        entity.setCharacterName(characterName);
        entity.setPlanetId(planetId);
        entity.setPlanetName(planetName);

        return entity;
    }
}