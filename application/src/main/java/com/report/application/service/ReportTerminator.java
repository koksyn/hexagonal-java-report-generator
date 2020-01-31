package com.report.application.service;

import com.report.application.domain.vo.ReportId;
import com.report.application.port.driven.ReportRepository;
import com.report.common.exception.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
public class ReportTerminator implements com.report.application.domain.port.driver.ReportTerminator {
    private final ReportRepository reportRepository;

    @Override
    @Transactional
    public void delete(@NonNull ReportId reportId) {
        reportRepository.getComplete(reportId)
                .orElseThrow(() -> new NotFoundException("Cannot delete, because Report was not found."));

        reportRepository.deleteComplete(reportId);
    }

    @Override
    @Transactional
    public void deleteAll() {
        reportRepository.deleteAllComplete();
    }
}
