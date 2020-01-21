package com.report.application.service;

import com.report.application.port.driven.SwapiRepository;
import com.report.common.vo.PositiveInteger;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SwapiCacheTest {
    @Mock
    private ExecutorService executorService;
    @Mock
    private FilmCrawlerTask filmCharacterTask;
    @Mock
    private PlanetCrawlerTask planetCrawlerTask;
    @Mock
    private CharacterCrawlerTask characterCrawlerTask;

    @Mock
    private SwapiRepository swapiRepository;
    @Mock
    private ExecutorServiceFactory executorServiceFactory;
    @Mock
    private FilmCrawlerTaskFactory filmCrawlerTaskFactory;
    @Mock
    private PlanetCrawlerTaskFactory planetCrawlerTaskFactory;
    @Mock
    private CharacterCrawlerTaskFactory characterCrawlerTaskFactory;

    @InjectMocks
    private SwapiCache cache;

    @BeforeEach
    void prepare() {
        // Given
        when(executorServiceFactory.buildWithSize(any(PositiveInteger.class)))
                .thenReturn(executorService);
        when(filmCrawlerTaskFactory.build())
                .thenReturn(filmCharacterTask);
        when(planetCrawlerTaskFactory.build())
                .thenReturn(planetCrawlerTask);
        when(characterCrawlerTaskFactory.build())
                .thenReturn(characterCrawlerTask);
    }

    @AfterEach
    void ensureThatScenarioAlwaysOccurs() {
        // Then
        verify(swapiRepository).deleteSwapiData();
        verify(executorService).execute(filmCharacterTask);
        verify(executorService).execute(planetCrawlerTask);
        verify(executorService).execute(characterCrawlerTask);
        verify(executorService).shutdown();
    }

    @Test
    @SneakyThrows
    @DisplayName("Refreshing cache, when it does not exceed the execution time limit")
    void shouldCrawlItAgainWithoutInterruptions() {
        // Given
        when(executorService.awaitTermination(anyLong(), any(TimeUnit.class)))
                .thenReturn(true);

        // When
        cache.refresh();

        // Then
        verify(executorService, never()).shutdownNow();
    }

    @Test
    @SneakyThrows
    @DisplayName("Refreshing cache, when it does exceed the execution time limit")
    void shouldShutdownAllCrawlingTasks() {
        // Given
        when(executorService.awaitTermination(anyLong(), any(TimeUnit.class)))
                .thenReturn(false);

        // When
        cache.refresh();

        // Then
        verify(executorService).shutdownNow();
    }

    @Test
    @SneakyThrows
    @DisplayName("Refreshing cache, when tasks were interrupted")
    void shouldHandleException() {
        // Given
        when(executorService.awaitTermination(anyLong(), any(TimeUnit.class)))
                .thenThrow(InterruptedException.class);

        // When
        cache.refresh();

        // Then
        verify(executorService).shutdownNow();
    }
}
