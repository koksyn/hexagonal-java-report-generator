package com.report.adapter.swapi.client.service;

import com.report.adapter.swapi.client.vo.EndpointName;
import com.report.application.entity.Character;
import lombok.NonNull;
import org.modelmapper.ModelMapper;

import java.util.List;

public final class CharacterCrawler extends SwapiCrawler<Character> implements com.report.application.port.driven.CharacterCrawler {
    public CharacterCrawler(@NonNull ResultCrawler resultCrawler,
                            @NonNull ModelMapper modelMapper) {
        super(resultCrawler, modelMapper);
    }

    @Override
    protected EndpointName getEndpointName() {
        return new EndpointName("people");
    }

    @Override
    public List<Character> crawl() {
        return crawlEndpoint();
    }
}
