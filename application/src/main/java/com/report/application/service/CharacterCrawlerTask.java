package com.report.application.service;

import com.report.application.entity.Character;
import com.report.application.port.driven.CharacterCrawler;
import com.report.application.port.driven.CharacterRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CharacterCrawlerTask implements Runnable {
    private final CharacterCrawler characterCrawler;
    private final CharacterRepository characterRepository;

    @Override
    public void run() {
        List<Character> characters = characterCrawler.crawl();
        characterRepository.saveAll(characters);
    }
}