package com.report.adapter.swapi.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.report.adapter.swapi.client.vo.LongIdSet;
import com.report.adapter.swapi.client.vo.Result;
import com.report.adapter.swapi.client.vo.UrlIterator;
import com.report.application.entity.Planet;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

import java.util.Iterator;

@RequiredArgsConstructor
public class ResultToPlanetConverter extends AbstractConverter<Result, Planet> {
    private final ModelMapper modelMapper;

    @Override
    protected Planet convert(Result result) {
        if (result == null) {
            return null;
        }

        JsonNode node = result.getRaw();

        String url = node.get("url")
                .asText();
        Long planetId = modelMapper.map(url, Long.class);

        String planetName = node.get("name")
                .asText();

        Iterator<JsonNode> filmNodes = node.get("films")
                .elements();
        UrlIterator filmUrlIterator = new UrlIterator(filmNodes);
        LongIdSet filmIds = modelMapper.map(filmUrlIterator, LongIdSet.class);

        Iterator<JsonNode> characterNodes = node.get("residents")
                .elements();
        UrlIterator characterUrlIterator = new UrlIterator(characterNodes);
        LongIdSet characterIds = modelMapper.map(characterUrlIterator, LongIdSet.class);

        return new Planet(
                planetId,
                planetName,
                filmIds.getRaw(),
                characterIds.getRaw()
        );
    }
}