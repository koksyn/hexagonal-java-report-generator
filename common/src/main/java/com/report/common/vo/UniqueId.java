package com.report.common.vo;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

public abstract class UniqueId {
    @Getter
    private final UUID raw;

    public UniqueId() {
        this.raw = UUID.randomUUID();
    }

    public UniqueId(@NonNull final UUID raw) {
        this.raw = raw;
    }
}
