package com.report.adapter.persistence.repository;

import com.report.application.domain.type.ReportStatus;
import com.report.application.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReportJpaRepository extends JpaRepository<Report, UUID> {
    long countByStatusEquals(ReportStatus status);

    Optional<Report> findByReportIdAndStatusEquals(UUID id, ReportStatus status);

    List<Report> findAllByStatusEquals(ReportStatus status);

    void deleteByReportIdAndStatusEquals(UUID id, ReportStatus status);

    void deleteAllByStatusEquals(ReportStatus status);
}