package com.report.application.domain.vo;

import lombok.Getter;
import lombok.NonNull;

public class CharacterPhrase {
    @Getter
    private final String raw;

    public CharacterPhrase(@NonNull final String raw) {
        this.raw = raw;
    }

    public boolean differ(@NonNull CharacterPhrase characterPhrase) {
        return !getRaw().equals(characterPhrase.getRaw());
    }
}