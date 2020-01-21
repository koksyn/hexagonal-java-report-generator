package com.report.adapter.swapi.client.service;

import com.report.adapter.swapi.client.vo.EndpointName;
import com.report.application.entity.Film;
import lombok.NonNull;
import org.modelmapper.ModelMapper;

import java.util.List;

public final class FilmCrawler extends SwapiCrawler<Film> implements com.report.application.port.driven.FilmCrawler {
    public FilmCrawler(@NonNull ResultCrawler resultCrawler,
                       @NonNull ModelMapper modelMapper) {
        super(resultCrawler, modelMapper);
    }

    @Override
    protected EndpointName getEndpointName() {
        return new EndpointName("films");
    }

    @Override
    public List<Film> crawl() {
        return crawlEndpoint();
    }
}
