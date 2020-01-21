package com.report.adapter.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.report.application.domain.vo.CharacterPhrase;
import com.report.application.domain.vo.PlanetName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryCriteria {
    @NotNull
    @JsonProperty("query_criteria_character_phrase")
    private String characterPhrase;

    @NotNull
    @NotEmpty
    @JsonProperty("query_criteria_planet_name")
    private String planetName;

    @JsonIgnore
    public CharacterPhrase toCharacterPhrase() {
        return new CharacterPhrase(characterPhrase);
    }

    @JsonIgnore
    public PlanetName toPlanetName() {
        return new PlanetName(planetName);
    }
}
