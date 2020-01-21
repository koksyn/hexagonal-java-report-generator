package com.report.adapter.persistence.config;

import com.report.adapter.persistence.repository.*;
import com.report.adapter.persistence.repository.CharacterRepository.CharacterJpaRepository;
import com.report.adapter.persistence.repository.FilmRepository.FilmJpaRepository;
import com.report.adapter.persistence.repository.PlanetRepository.PlanetJpaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManagerFactory;

@Configuration
@EntityScan("com.report.application.entity")
@EnableJpaRepositories(basePackages = "com.report.adapter.persistence.repository", considerNestedRepositories = true)
public class BeanPersistenceConfig {
    @Bean
    SwapiNativeRepository swapiNativeRepository(EntityManagerFactory entityManagerFactory) {
        return new SwapiNativeRepository(entityManagerFactory);
    }

    /*
     *  Adapters for driven ports
     */

    @Bean
    com.report.application.port.driven.CharacterRepository characterRepository(CharacterJpaRepository characterJpaRepository) {
        return new CharacterRepository(characterJpaRepository);
    }

    @Bean
    com.report.application.port.driven.FilmRepository filmRepository(FilmJpaRepository filmJpaRepository) {
        return new FilmRepository(filmJpaRepository);
    }

    @Bean
    com.report.application.port.driven.PlanetRepository roomRepository(PlanetJpaRepository planetJpaRepository) {
        return new PlanetRepository(planetJpaRepository);
    }

    @Bean
    com.report.application.port.driven.ReportRepository reportRepository(ReportJpaRepository reportJpaRepository,
                                                                         ModelMapper modelMapper) {
        return new ReportRepository(reportJpaRepository, modelMapper);
    }

    @Bean
    com.report.application.port.driven.SwapiRepository swapiRepository(SwapiNativeRepository swapiNativeRepository) {
        return new SwapiRepository(swapiNativeRepository);
    }
}