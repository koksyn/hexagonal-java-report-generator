package com.report.application.service;

import com.report.application.entity.Character;
import com.report.application.port.driven.CharacterCrawler;
import com.report.application.port.driven.CharacterRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CharacterCrawlerTaskTest {
    @Mock
    private List<Character> characters;
    @Mock
    private CharacterCrawler characterCrawler;
    @Mock
    private CharacterRepository characterRepository;

    @InjectMocks
    private CharacterCrawlerTask task;

    @Test
    @DisplayName("Running task")
    void shouldCrawlAndSaveAllResults() {
        // Given
        when(characterCrawler.crawl()).thenReturn(characters);

        // When
        task.run();

        // Then
        verify(characterRepository).saveAll(characters);
    }
}
