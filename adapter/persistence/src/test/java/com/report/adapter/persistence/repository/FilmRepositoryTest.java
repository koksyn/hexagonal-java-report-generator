package com.report.adapter.persistence.repository;

import com.report.adapter.persistence.repository.FilmRepository.FilmJpaRepository;
import com.report.application.entity.Film;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FilmRepositoryTest {
    @Mock
    private List<Film> films;

    @Mock
    private FilmJpaRepository jpaRepository;

    @InjectMocks
    private FilmRepository repository;

    @Test
    @DisplayName("Saving all, when null was provided")
    void shouldNotAcceptNullValue() {
        // When & Then
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> repository.saveAll(null)
        );

        assertTrue(exception
                .getMessage()
                .contains("is marked non-null but is null")
        );
    }

    @Test
    @DisplayName("Saving all, when list of films was provided")
    void shouldSaveAllThroughJpaRepository() {
        // When
        repository.saveAll(films);

        // Then
        verify(jpaRepository).saveAll(films);
    }
}
