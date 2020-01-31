package com.report.common.vo;

import com.google.common.primitives.UnsignedLong;
import lombok.Getter;
import lombok.NonNull;

public abstract class UnsignedLongId {
    @Getter
    private final UnsignedLong raw;

    public UnsignedLongId(@NonNull final UnsignedLong raw) {
        this.raw = raw;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UnsignedLongId) {
            UnsignedLongId unsignedLongId = (UnsignedLongId) obj;

            return raw.equals(unsignedLongId.getRaw());
        }

        return false;
    }
}
