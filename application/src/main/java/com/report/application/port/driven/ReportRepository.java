package com.report.application.port.driven;

import com.report.application.domain.vo.ReportId;
import com.report.application.entity.Report;

import java.util.List;
import java.util.Optional;

public interface ReportRepository {
    Optional<Report> get(ReportId reportId);

    Optional<Report> getComplete(ReportId reportId);

    List<Report> getAllComplete();

    List<com.report.application.domain.Report> getAllIncomplete();

    boolean anyIncomplete();

    void deleteComplete(ReportId reportId);

    void deleteAllComplete();

    void save(com.report.application.domain.Report report);

    void saveAll(List<com.report.application.domain.Report> reports);
}
