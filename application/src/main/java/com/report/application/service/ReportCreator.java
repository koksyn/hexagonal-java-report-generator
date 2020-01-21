package com.report.application.service;

import com.report.application.domain.Report;
import com.report.application.port.driven.ReportRepository;
import com.report.application.domain.snapshot.ReportSnapshot;
import com.report.application.domain.vo.CharacterPhrase;
import com.report.application.domain.vo.PlanetName;
import com.report.application.domain.vo.ReportId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.transaction.Transactional;

@RequiredArgsConstructor
public class ReportCreator implements com.report.application.port.driver.ReportCreator {
    private final ReportRepository reportRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public void createOrReplace(
            @NonNull ReportId reportId,
            @NonNull CharacterPhrase characterPhrase,
            @NonNull PlanetName planetName) {
        Report report = getOrCreateIfDoesNotExist(reportId, characterPhrase, planetName);

        report.prepareForProcessing(characterPhrase, planetName);

        reportRepository.save(report);
    }

    private Report getOrCreateIfDoesNotExist(
            ReportId reportId,
            CharacterPhrase characterPhrase,
            PlanetName planetName) {
        return reportRepository.get(reportId)
                .map(entity -> modelMapper.map(entity, ReportSnapshot.class))
                .map(Report::fromSnapshot)
                .orElseGet(() -> new Report(reportId, characterPhrase, planetName));
    }
}