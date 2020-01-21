package com.report.adapter.persistence.repository;

import com.report.application.entity.Film;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@RequiredArgsConstructor
public class FilmRepository implements com.report.application.port.driven.FilmRepository {
    public interface FilmJpaRepository extends JpaRepository<Film, Long> {}

    private final FilmJpaRepository filmJpaRepository;

    @Override
    public void saveAll(@NonNull List<Film> films) {
        filmJpaRepository.saveAll(films);
    }
}
