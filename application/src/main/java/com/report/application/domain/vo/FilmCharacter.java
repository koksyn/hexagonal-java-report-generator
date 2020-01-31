package com.report.application.domain.vo;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class FilmCharacter {
    private final FilmId filmId;
    private final FilmName filmName;
    private final CharacterId characterId;
    private final CharacterName characterName;
    private final PlanetId planetId;
    private final PlanetName planetName;

    public FilmCharacter(@NonNull final FilmId filmId,
                         @NonNull final FilmName filmName,
                         @NonNull final CharacterId characterId,
                         @NonNull final CharacterName characterName,
                         @NonNull final PlanetId planetId,
                         @NonNull final PlanetName planetName) {
        this.filmId = filmId;
        this.filmName = filmName;
        this.characterId = characterId;
        this.characterName = characterName;
        this.planetId = planetId;
        this.planetName = planetName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof FilmCharacter) {
            FilmCharacter filmCharacter = (FilmCharacter) obj;

            return filmCharacter.filmId.equals(filmId) &&
                    filmCharacter.filmName.equals(filmName) &&
                    filmCharacter.characterId.equals(characterId) &&
                    filmCharacter.characterName.equals(characterName) &&
                    filmCharacter.planetId.equals(planetId) &&
                    filmCharacter.planetName.equals(planetName);
        }

        return false;
    }
}
