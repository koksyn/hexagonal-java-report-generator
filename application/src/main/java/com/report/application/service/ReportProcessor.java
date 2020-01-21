package com.report.application.service;

import com.report.application.domain.Report;
import com.report.application.port.driven.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
public class ReportProcessor {
    private final ReportFulfillment reportFulfillment;
    private final ReportRepository reportRepository;
    private final SwapiCache swapiCache;

    @Scheduled(fixedDelay = 5000) // 5 seconds
    public void tryProcessIncompleteReports() {
        boolean couldBeProcessed = reportRepository.anyIncomplete();

        if(couldBeProcessed) {
            swapiCache.refresh();
            processIncompleteReports();
        }
    }

    @Transactional
    protected void processIncompleteReports() {
        List<Report> reports = reportRepository.getAllIncomplete();
        reports.forEach(reportFulfillment::fillWithFilmCharacters);

        reportRepository.saveAll(reports);
    }
}