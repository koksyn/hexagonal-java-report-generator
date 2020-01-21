package com.report.application.service;

import com.report.application.port.driven.FilmCrawler;
import com.report.application.port.driven.FilmRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FilmCrawlerTaskFactory {
    private final FilmCrawler filmCrawler;
    private final FilmRepository filmRepository;

    FilmCrawlerTask build() {
        return new FilmCrawlerTask(filmCrawler, filmRepository);
    }
}
