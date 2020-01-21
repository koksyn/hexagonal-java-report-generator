package com.report.adapter.swapi.client.vo;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.NonNull;

import java.util.Iterator;

public class UrlIterator {
    @Getter
    private final Iterator<JsonNode> raw;

    public UrlIterator(@NonNull final Iterator<JsonNode> raw) {
        this.raw = raw;
    }
}