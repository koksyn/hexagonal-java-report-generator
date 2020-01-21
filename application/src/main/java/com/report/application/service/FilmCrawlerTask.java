package com.report.application.service;

import com.report.application.entity.Film;
import com.report.application.port.driven.FilmCrawler;
import com.report.application.port.driven.FilmRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FilmCrawlerTask implements Runnable {
    private final FilmCrawler filmCrawler;
    private final FilmRepository filmRepository;

    @Override
    public void run() {
        List<Film> films = filmCrawler.crawl();
        filmRepository.saveAll(films);
    }
}