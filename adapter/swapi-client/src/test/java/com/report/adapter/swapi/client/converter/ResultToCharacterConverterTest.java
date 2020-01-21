package com.report.adapter.swapi.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.report.adapter.swapi.client.vo.Result;
import com.report.application.entity.Character;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResultToCharacterConverterTest {
    private static final Long CHARACTER_ID = 1L;
    private static final String CHARACTER_NAME = "Luke Skywalker";
    private static final String URL = "https://swapi.co/api/people/1/";

    @Mock
    private JsonNode node;

    @InjectMocks
    private Result result;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ResultToCharacterConverter converter;

    @Test
    @DisplayName("Converting null")
    void shouldReturnNull() {
        // When
        Character character = converter.convert((Result) null);

        // Then
        assertNull(character);
    }

    @Test
    @DisplayName("Converting result")
    void shouldReturnCharacter() {
        // Given
        when(node.get(any(String.class)))
                .thenReturn(node, node);

        when(node.asText())
                .thenReturn(URL, CHARACTER_NAME);

        when(modelMapper.map(URL, Long.class))
                .thenReturn(CHARACTER_ID);

        // When
        Character character = converter.convert(result);

        // Then
        assertEquals(CHARACTER_ID, character.getId());
        assertEquals(CHARACTER_NAME, character.getName());

        verify(modelMapper).map(URL, Long.class);
    }
}
