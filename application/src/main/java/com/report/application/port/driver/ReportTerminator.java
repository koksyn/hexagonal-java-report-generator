package com.report.application.port.driver;

import com.report.application.domain.vo.ReportId;

public interface ReportTerminator {
    void delete(ReportId reportId);

    void deleteAll();
}
