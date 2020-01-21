package com.report.adapter.persistence.repository;

import com.report.application.entity.Character;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@RequiredArgsConstructor
public class CharacterRepository implements com.report.application.port.driven.CharacterRepository {
    public interface CharacterJpaRepository extends JpaRepository<Character, Long> {}

    private final CharacterJpaRepository characterJpaRepository;

    @Override
    public void saveAll(@NonNull List<Character> characters) {
        characterJpaRepository.saveAll(characters);
    }
}
