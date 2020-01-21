package com.report.application.service;

import com.report.application.entity.Planet;
import com.report.application.port.driven.PlanetCrawler;
import com.report.application.port.driven.PlanetRepository;
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
class PlanetCrawlerTaskTest {
    @Mock
    private List<Planet> planets;
    @Mock
    private PlanetCrawler crawler;
    @Mock
    private PlanetRepository repository;

    @InjectMocks
    private PlanetCrawlerTask task;

    @Test
    @DisplayName("Running task")
    void shouldCrawlAndSaveAllResults() {
        // Given
        when(crawler.crawl()).thenReturn(planets);

        // When
        task.run();

        // Then
        verify(repository).saveAll(planets);
    }
}
