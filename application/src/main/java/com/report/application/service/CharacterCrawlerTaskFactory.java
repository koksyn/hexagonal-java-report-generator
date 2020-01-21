package com.report.application.service;

import com.report.application.port.driven.CharacterCrawler;
import com.report.application.port.driven.CharacterRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CharacterCrawlerTaskFactory {
    private final CharacterCrawler characterCrawler;
    private final CharacterRepository characterRepository;

    CharacterCrawlerTask build() {
        return new CharacterCrawlerTask(characterCrawler, characterRepository);
    }
}
