package com.report.application.service;

import com.report.application.entity.Planet;
import com.report.application.port.driven.PlanetCrawler;
import com.report.application.port.driven.PlanetRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PlanetCrawlerTask implements Runnable {
    private final PlanetCrawler planetCrawler;
    private final PlanetRepository planetRepository;

    @Override
    public void run() {
        List<Planet> planets = planetCrawler.crawl();
        planetRepository.saveAll(planets);
    }
}