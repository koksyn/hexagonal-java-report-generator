package com.report.application.port.driven;

import com.report.application.entity.Film;

import java.util.List;

public interface FilmCrawler {
    List<Film> crawl();
}
