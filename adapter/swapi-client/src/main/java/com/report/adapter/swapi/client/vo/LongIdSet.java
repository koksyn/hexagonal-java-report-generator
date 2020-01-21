package com.report.adapter.swapi.client.vo;

import lombok.Getter;
import lombok.NonNull;

import java.util.Set;

public class LongIdSet {
    @Getter
    private final Set<Long> raw;

    public LongIdSet(@NonNull final Set<Long> raw) {
        this.raw = raw;
    }
}