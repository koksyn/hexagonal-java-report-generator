package com.report.application.port.driven;

import com.report.application.entity.Character;

import java.util.List;

public interface CharacterCrawler {
    List<Character> crawl();
}
