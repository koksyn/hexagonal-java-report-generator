package com.report.application.domain.vo;

import lombok.Getter;
import lombok.NonNull;

import java.util.LinkedList;
import java.util.List;

public class FilmCharacterList {
    @Getter
    private final List<FilmCharacter> raw;

    public FilmCharacterList() {
        raw = new LinkedList<>();
    }

    public FilmCharacterList(@NonNull final List<FilmCharacter> raw) {
        this.raw = raw;
    }

    public void add(@NonNull final FilmCharacter filmCharacter) {
        raw.add(filmCharacter);
    }

    public void clear() {
        raw.clear();
    }
}