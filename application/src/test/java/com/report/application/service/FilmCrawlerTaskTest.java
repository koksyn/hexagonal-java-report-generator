package com.report.application.service;

import com.report.application.entity.Film;
import com.report.application.port.driven.FilmCrawler;
import com.report.application.port.driven.FilmRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmCrawlerTaskTest {
    @Mock
    private List<Film> films;
    @Mock
    private FilmCrawler crawler;
    @Mock
    private FilmRepository repository;

    @InjectMocks
    private FilmCrawlerTask task;

    @Test
    @DisplayName("Running task")
    void shouldCrawlAndSaveAllResults() {
        // Given
        when(crawler.crawl()).thenReturn(films);

        // When
        task.run();

        // Then
        verify(repository).saveAll(films);
    }
}
