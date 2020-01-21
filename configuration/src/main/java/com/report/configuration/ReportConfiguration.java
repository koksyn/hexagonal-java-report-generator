package com.report.configuration;

import com.report.adapter.persistence.converter.FilmCharacterValueObjectToEntityConverter;
import com.report.adapter.persistence.converter.ReportSnapshotToEntityConverter;
import com.report.adapter.swapi.client.converter.*;
import com.report.application.converter.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.report.adapter.swapi.client.config")
@ComponentScan("com.report.adapter.persistence.config")
@ComponentScan("com.report.adapter.api.config")
@EnableScheduling
@EnableTransactionManagement
public class ReportConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        addSwapiClientConverters(modelMapper);
        addPersistenceConverters(modelMapper);
        addApplicationConverters(modelMapper);

        return modelMapper;
    }

    private void addSwapiClientConverters(ModelMapper modelMapper) {
        modelMapper.addConverter(new StringUrlToLongIdConverter());
        modelMapper.addConverter(new UrlIteratorToLongIdSetConverter(modelMapper));
        modelMapper.addConverter(new ResultToFilmConverter(modelMapper));
        modelMapper.addConverter(new ResultToPlanetConverter(modelMapper));
        modelMapper.addConverter(new ResultToCharacterConverter(modelMapper));
    }

    private void addPersistenceConverters(ModelMapper modelMapper) {
        modelMapper.addConverter(new FilmCharacterValueObjectToEntityConverter());
        modelMapper.addConverter(new ReportSnapshotToEntityConverter(modelMapper));
    }

    private void addApplicationConverters(ModelMapper modelMapper) {
        modelMapper.addConverter(new FilmCharacterEntityToDetailsConverter());
        modelMapper.addConverter(new FilmCharacterEntityToValueObjectConverter());
        modelMapper.addConverter(new FilmCharacterRecordToValueObjectConverter());
        modelMapper.addConverter(new ReportEntityToDetailsConverter(modelMapper));
        modelMapper.addConverter(new ReportEntityToSnapshotConverter(modelMapper));
    }
}