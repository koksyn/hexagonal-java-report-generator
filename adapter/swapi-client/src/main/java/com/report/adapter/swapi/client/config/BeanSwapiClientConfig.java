package com.report.adapter.swapi.client.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.report.adapter.swapi.client.service.*;
import com.report.common.vo.PositiveInteger;
import okhttp3.OkHttpClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.concurrent.TimeUnit;

@Configuration
@ComponentScan("com.report.adapter.swapi.client.service")
public class BeanSwapiClientConfig {
    @Bean
    OkHttpClient okHttpClient(@Value("${swapi.client.request-seconds-timeout}") short requestTimeout) {
        return new OkHttpClient().newBuilder()
                .followRedirects(false)
                .readTimeout(requestTimeout, TimeUnit.SECONDS)
                .build();
    }

    @Bean
    public DailyRateCounter dailyRateCounter(
            @Value("${swapi.client.requests-per-day-limit}") int limit) {
        PositiveInteger requestsPerDayLimit = new PositiveInteger(limit);

        return new DailyRateCounter(requestsPerDayLimit, Clock.systemDefaultZone());
    }

    @Bean
    public ResultCrawler resultCrawler(OkHttpClient okHttpClient,
                                       ObjectMapper objectMapper,
                                       DailyRateCounter dailyRateCounter,
                                       @Value("${swapi.client.base-url}") String baseUrl) {
        return new ResultCrawler(okHttpClient, objectMapper, dailyRateCounter, baseUrl);
    }

    /*
     *  Adapters for driven ports
     */

    @Bean
    public com.report.application.port.driven.CharacterCrawler characterCrawler(ResultCrawler resultCrawler,
                                                                                ModelMapper modelMapper) {
        return new CharacterCrawler(resultCrawler, modelMapper);
    }

    @Bean
    public com.report.application.port.driven.PlanetCrawler planetCrawler(ResultCrawler resultCrawler,
                                                                          ModelMapper modelMapper) {
        return new PlanetCrawler(resultCrawler, modelMapper);
    }

    @Bean
    public com.report.application.port.driven.FilmCrawler filmCrawler(ResultCrawler resultCrawler,
                                                                      ModelMapper modelMapper) {
        return new FilmCrawler(resultCrawler, modelMapper);
    }
}