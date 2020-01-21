package com.report.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmCharacterRecord {
    private Long filmId;
    private String filmName;
    private Long characterId;
    private String characterName;
    private Long planetId;
    private String planetName;
}
