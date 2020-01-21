package com.report.adapter.swapi.client.converter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StringUrlToLongIdConverterTest {
    private static final Long ID = 1337L;
    private static final String URL = "https://swapi.co/api/starships/1337/";

    private static StringUrlToLongIdConverter converter;

    @BeforeAll
    static void init() {
        converter = new StringUrlToLongIdConverter();
    }

    @Test
    @DisplayName("Converting null")
    void shouldReturnNull() {
        // When
        Long longId = converter.convert((String) null);

        // Then
        assertNull(longId);
    }

    @ParameterizedTest
    @MethodSource("inputsAndResults")
    @DisplayName("Converting URL")
    void shouldReturnLongId(String url, Long id) {
        // When
        Long longId = converter.convert(url);

        // Then
        assertEquals(id, longId);
    }

    private static Stream<Arguments> inputsAndResults() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of("", null),
                Arguments.of("   ", null),
                Arguments.of("https://swapi.co/api/films/", null),
                Arguments.of("https://swapi.co/api/characters1234", null),
                Arguments.of("7", 7L),
                Arguments.of("https://swapi.co/api/planets/5", 5L),
                Arguments.of("https://swapi.co/api/starships/1337/", 1337L),
                Arguments.of("http://abc.def/123/", 123L),
                Arguments.of("https://xyz.abc/4321/", 4321L)
        );
    }
}
