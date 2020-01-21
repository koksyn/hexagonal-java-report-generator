package com.report.adapter.persistence.repository;

import com.report.application.domain.snapshot.ReportSnapshot;
import com.report.application.domain.type.ReportStatus;
import com.report.application.domain.vo.ReportId;
import com.report.application.entity.Report;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ReportRepository implements com.report.application.port.driven.ReportRepository {
    private final ReportJpaRepository reportJpaRepository;
    private final ModelMapper modelMapper;

    @Override
    public Optional<Report> get(@NonNull ReportId reportId) {
        return reportJpaRepository.findById(reportId.getRaw());
    }

    @Override
    public Optional<Report> getComplete(@NonNull ReportId reportId) {
        return reportJpaRepository.findByReportIdAndStatusEquals(reportId.getRaw(), ReportStatus.COMPLETE);
    }

    @Override
    public List<Report> getAllComplete() {
        return reportJpaRepository.findAllByStatusEquals(ReportStatus.COMPLETE);
    }

    @Override
    public List<com.report.application.domain.Report> getAllIncomplete() {
        return reportJpaRepository.findAllByStatusEquals(ReportStatus.INCOMPLETE)
                .stream()
                .map(entity -> modelMapper.map(entity, ReportSnapshot.class))
                .map(com.report.application.domain.Report::fromSnapshot)
                .collect(Collectors.toList());
    }

    @Override
    public boolean anyIncomplete() {
        return reportJpaRepository.countByStatusEquals(ReportStatus.INCOMPLETE) > 0;
    }

    @Override
    public void deleteComplete(@NonNull ReportId reportId) {
        reportJpaRepository.deleteByReportIdAndStatusEquals(reportId.getRaw(), ReportStatus.COMPLETE);
    }

    @Override
    public void deleteAllComplete() {
        reportJpaRepository.deleteAllByStatusEquals(ReportStatus.COMPLETE);
    }

    @Override
    public void save(@NonNull com.report.application.domain.Report report) {
        ReportSnapshot snapshot = report.toSnapshot();
        Report entity = modelMapper.map(snapshot, Report.class);

        reportJpaRepository.save(entity);
    }

    @Override
    public void saveAll(@NonNull List<com.report.application.domain.Report> reports) {
        List<Report> entities = reports.stream()
                .map(com.report.application.domain.Report::toSnapshot)
                .map(snapshot -> modelMapper.map(snapshot, Report.class))
                .collect(Collectors.toList());

        reportJpaRepository.saveAll(entities);
    }
}
