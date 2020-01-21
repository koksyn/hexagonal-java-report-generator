package com.report.adapter.swapi.client.vo;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.NonNull;

public class Result {
    @Getter
    private final JsonNode raw;

    public Result(@NonNull final JsonNode raw) {
        this.raw = raw;
    }
}