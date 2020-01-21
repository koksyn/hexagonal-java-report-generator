package com.report.application.service;

import com.report.application.port.driven.SwapiRepository;
import com.report.common.vo.PositiveInteger;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class SwapiCache {
    private static final PositiveInteger poolSize = new PositiveInteger(3);

    private final SwapiRepository swapiRepository;
    private final ExecutorServiceFactory executorServiceFactory;
    private final FilmCrawlerTaskFactory filmCrawlerTaskFactory;
    private final PlanetCrawlerTaskFactory planetCrawlerTaskFactory;
    private final CharacterCrawlerTaskFactory characterCrawlerTaskFactory;

    void refresh() {
        swapiRepository.deleteSwapiData();
        crawlSwapiData();
    }

    private void crawlSwapiData() {
        final ExecutorService executor = executorServiceFactory.buildWithSize(poolSize);

        Arrays.asList(
                filmCrawlerTaskFactory.build(),
                planetCrawlerTaskFactory.build(),
                characterCrawlerTaskFactory.build()
        ).forEach(executor::execute);

        executor.shutdown();

        try {
            if (!executor.awaitTermination(3, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            executor.shutdownNow();
        }
    }
}
