package com.report.adapter.persistence.repository;

import com.report.application.entity.Character;
import com.report.adapter.persistence.repository.CharacterRepository.CharacterJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CharacterRepositoryTest {
    @Mock
    private List<Character> characters;

    @Mock
    private CharacterJpaRepository jpaRepository;

    @InjectMocks
    private CharacterRepository repository;

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
    @DisplayName("Saving all, when list of characters was provided")
    void shouldSaveAllThroughJpaRepository() {
        // When
        repository.saveAll(characters);

        // Then
        verify(jpaRepository).saveAll(characters);
    }
}
