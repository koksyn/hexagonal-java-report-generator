package com.report.adapter.persistence.repository;

import com.report.application.entity.Planet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@RequiredArgsConstructor
public class PlanetRepository implements com.report.application.port.driven.PlanetRepository {
    public interface PlanetJpaRepository extends JpaRepository<Planet, Long> {}

    private final PlanetJpaRepository planetJpaRepository;

    @Override
    public void saveAll(@NonNull List<Planet> planets) {
        planetJpaRepository.saveAll(planets);
    }
}
