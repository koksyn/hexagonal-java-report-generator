package com.report.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmCharacterDetails {
    private String filmId;
    private String filmName;
    private String characterId;
    private String characterName;
    private String planetId;
    private String planetName;
}
