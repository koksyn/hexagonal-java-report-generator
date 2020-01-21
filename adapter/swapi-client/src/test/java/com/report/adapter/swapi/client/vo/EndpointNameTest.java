package com.report.adapter.swapi.client.vo;

import com.report.common.unit.vo.NonBlankNameTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EndpointNameTest extends NonBlankNameTest<EndpointName> {
    @Override
    protected Class<EndpointName> getClassOfTheInstanceBeingCreated() {
        return EndpointName.class;
    }

    @Test
    @DisplayName("Generating path for null")
    void shouldNotAcceptNullValueOfBaseUrl() {
        // Given
        EndpointName endpointName = new EndpointName("test");

        // When & Then
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> endpointName.generatePathForBaseUrl(null)
        );

        assertTrue(exception
                .getMessage()
                .contains("is marked non-null but is null")
        );
    }

    @ParameterizedTest
    @MethodSource("validData")
    @DisplayName("Generating path for valid data")
    void shouldGenerateFullPath(String baseUrl, String endpoint, String path) {
        // Given
        EndpointName endpointName = new EndpointName(endpoint);

        // When
        String result = endpointName.generatePathForBaseUrl(baseUrl);

        // Then
        assertEquals(path, result);
    }

    private static Stream<Arguments> validData() {
        return Stream.of(
                Arguments.of("", "people", "people/"),
                Arguments.of("swapi.co/", "stars", "swapi.co/stars/"),
                Arguments.of("http://abc.def/", "films", "http://abc.def/films/"),
                Arguments.of("https://xyz.abc/", "planets", "https://xyz.abc/planets/")
        );
    }
}
