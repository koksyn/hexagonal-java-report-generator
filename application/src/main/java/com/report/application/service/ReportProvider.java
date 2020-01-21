package com.report.application.service;

import com.report.application.dto.ReportDetails;
import com.report.application.port.driven.ReportRepository;
import com.report.application.domain.vo.ReportId;
import com.report.common.exception.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ReportProvider implements com.report.application.port.driver.ReportProvider {
    private final ReportRepository reportRepository;
    private final ModelMapper modelMapper;

    @Override
    public ReportDetails get(@NonNull ReportId reportId) {
        return reportRepository.getComplete(reportId)
                .map(report -> modelMapper.map(report, ReportDetails.class))
                .orElseThrow(() -> new NotFoundException("Report not found."));
    }

    @Override
    public List<ReportDetails> getAll() {
        return reportRepository.getAllComplete()
                .stream()
                .map(report -> modelMapper.map(report, ReportDetails.class))
                .collect(Collectors.toList());
    }
}
