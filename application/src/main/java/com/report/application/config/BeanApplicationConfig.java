package com.report.application.config;

import com.report.application.port.driven.*;
import com.report.application.port.driver.ReportCreator;
import com.report.application.port.driver.ReportProvider;
import com.report.application.port.driver.ReportTerminator;
import com.report.application.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.report.application.service")
public class BeanApplicationConfig {
    /*
     *  Adapters for driver ports
     */

    @Bean
    ReportCreator reportCreator(ReportRepository reportRepository, ModelMapper modelMapper) {
        return new com.report.application.service.ReportCreator(reportRepository, modelMapper);
    }

    @Bean
    ReportProvider reportProvider(ReportRepository reportRepository, ModelMapper modelMapper) {
        return new com.report.application.service.ReportProvider(reportRepository, modelMapper);
    }

    @Bean
    ReportTerminator reportTerminator(ReportRepository reportRepository) {
        return new com.report.application.service.ReportTerminator(reportRepository);
    }

    /*
     *  Other services
     */

    @Bean
    ReportFulfillment reportFulfillment(SwapiRepository swapiRepository, ModelMapper modelMapper) {
        return new ReportFulfillment(swapiRepository, modelMapper);
    }

    @Bean
    ExecutorServiceFactory executorServiceFactory() {
        return new ExecutorServiceFactory();
    }

    @Bean
    FilmCrawlerTaskFactory filmCrawlerTaskFactory(FilmCrawler filmCrawler, FilmRepository filmRepository) {
        return new FilmCrawlerTaskFactory(filmCrawler, filmRepository);
    }

    @Bean
    PlanetCrawlerTaskFactory planetCrawlerTaskFactory(PlanetCrawler planetCrawler, PlanetRepository planetRepository) {
        return new PlanetCrawlerTaskFactory(planetCrawler, planetRepository);
    }

    @Bean
    CharacterCrawlerTaskFactory characterCrawlerTaskFactory(CharacterCrawler characterCrawler, CharacterRepository characterRepository) {
        return new CharacterCrawlerTaskFactory(characterCrawler, characterRepository);
    }

    @Bean
    SwapiCache swapiCache(SwapiRepository swapiRepository,
                          ExecutorServiceFactory executorServiceFactory,
                          FilmCrawlerTaskFactory filmCrawlerTaskFactory,
                          PlanetCrawlerTaskFactory planetCrawlerTaskFactory,
                          CharacterCrawlerTaskFactory characterCrawlerTaskFactory) {
        return new SwapiCache(
                swapiRepository,
                executorServiceFactory,
                filmCrawlerTaskFactory,
                planetCrawlerTaskFactory,
                characterCrawlerTaskFactory
        );
    }

    @Bean
    ReportProcessor reportProcessor(ReportFulfillment reportFulfillment,
                                    ReportRepository reportRepository,
                                    SwapiCache swapiCache) {
        return new ReportProcessor(reportFulfillment, reportRepository, swapiCache);
    }
}