package com.report.application.domain.vo;

import lombok.NonNull;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FilmCharacterList {
    private final List<FilmCharacter> raw;

    public FilmCharacterList() {
        raw = new LinkedList<>();
    }

    public FilmCharacterList(@NonNull final List<FilmCharacter> raw) {
        this.raw = new LinkedList<>(raw);
    }

    public void add(@NonNull final FilmCharacter filmCharacter) {
        raw.add(filmCharacter);
    }

    public void clear() {
        raw.clear();
    }

    public List<FilmCharacter> getRaw() {
        return Collections.unmodifiableList(raw);
    }

    public boolean contains(@NonNull final FilmCharacter filmCharacter) {
        return raw.stream()
                .anyMatch(item -> item.equals(filmCharacter));
    }
}