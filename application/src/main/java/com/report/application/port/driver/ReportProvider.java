package com.report.application.port.driver;

import com.report.application.dto.ReportDetails;
import com.report.application.domain.vo.ReportId;

import java.util.List;

public interface ReportProvider {
    ReportDetails get(ReportId reportId);

    List<ReportDetails> getAll();
}
