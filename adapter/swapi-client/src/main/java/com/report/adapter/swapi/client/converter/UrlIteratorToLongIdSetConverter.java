package com.report.adapter.swapi.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.report.adapter.swapi.client.vo.LongIdSet;
import com.report.adapter.swapi.client.vo.UrlIterator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@RequiredArgsConstructor
public class UrlIteratorToLongIdSetConverter extends AbstractConverter<UrlIterator, LongIdSet> {
    private final ModelMapper modelMapper;

    @Override
    protected LongIdSet convert(UrlIterator urlIterator) {
        if(urlIterator == null) {
            return null;
        }

        Iterator<JsonNode> elements = urlIterator.getRaw();
        Set<Long> ids = new HashSet<>();

        while(elements.hasNext()) {
            addConvertedElementToIds(elements.next(), ids);
        }

        return new LongIdSet(ids);
    }

    private void addConvertedElementToIds(JsonNode element, Set<Long> ids) {
        String url = element.asText();
        Long id = modelMapper.map(url, Long.class);

        ids.add(id);
    }
}
