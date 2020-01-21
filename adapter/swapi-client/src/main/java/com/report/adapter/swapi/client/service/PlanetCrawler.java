package com.report.adapter.swapi.client.service;

import com.report.adapter.swapi.client.vo.EndpointName;
import com.report.application.entity.Planet;
import lombok.NonNull;
import org.modelmapper.ModelMapper;

import java.util.List;

public final class PlanetCrawler extends SwapiCrawler<Planet> implements com.report.application.port.driven.PlanetCrawler {
    public PlanetCrawler(@NonNull ResultCrawler resultCrawler,
                         @NonNull ModelMapper modelMapper) {
        super(resultCrawler, modelMapper);
    }

    @Override
    protected EndpointName getEndpointName() {
        return new EndpointName("planets");
    }

    @Override
    public List<Planet> crawl() {
        return crawlEndpoint();
    }
}
