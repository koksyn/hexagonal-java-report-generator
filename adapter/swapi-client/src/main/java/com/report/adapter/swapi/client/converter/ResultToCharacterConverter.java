package com.report.adapter.swapi.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.report.adapter.swapi.client.vo.Result;
import com.report.application.entity.Character;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

@RequiredArgsConstructor
public class ResultToCharacterConverter extends AbstractConverter<Result, Character> {
    private final ModelMapper modelMapper;

    @Override
    protected Character convert(Result result) {
        if (result == null) {
            return null;
        }

        JsonNode node = result.getRaw();

        String url = node.get("url")
                .asText();
        Long characterId = modelMapper.map(url, Long.class);

        String characterName = node.get("name")
                .asText();

        return new Character(
                characterId,
                characterName
        );
    }
}