package com.report.adapter.swapi.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.report.adapter.swapi.client.vo.LongIdSet;
import com.report.adapter.swapi.client.vo.Result;
import com.report.adapter.swapi.client.vo.UrlIterator;
import com.report.application.entity.Film;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

import java.util.Iterator;

@RequiredArgsConstructor
public class ResultToFilmConverter extends AbstractConverter<Result, Film> {
    private final ModelMapper modelMapper;

    @Override
    protected Film convert(Result result) {
        if (result == null) {
            return null;
        }

        JsonNode node = result.getRaw();

        String url = node.get("url")
                .asText();
        Long filmId = modelMapper.map(url, Long.class);

        String filmName = node.get("title")
                .asText();

        Iterator<JsonNode> characterNodes = node.get("characters")
                .elements();
        UrlIterator urlIterator = new UrlIterator(characterNodes);
        LongIdSet characterIds = modelMapper.map(urlIterator, LongIdSet.class);

        return new Film(
                filmId,
                filmName,
                characterIds.getRaw()
        );
    }
}