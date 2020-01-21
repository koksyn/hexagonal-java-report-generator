package com.report.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDetails {
    private UUID reportId;

    @JsonProperty("query_criteria_character_phrase")
    private String characterPhrase;

    @JsonProperty("query_criteria_planet_name")
    private String planetName;

    @JsonProperty("results")
    private List<FilmCharacterDetails> filmCharacters;
}