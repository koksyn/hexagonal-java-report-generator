package com.report.application.service;

import com.report.application.port.driven.PlanetCrawler;
import com.report.application.port.driven.PlanetRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlanetCrawlerTaskFactory {
    private final PlanetCrawler planetCrawler;
    private final PlanetRepository planetRepository;

    PlanetCrawlerTask build() {
        return new PlanetCrawlerTask(planetCrawler, planetRepository);
    }
}
