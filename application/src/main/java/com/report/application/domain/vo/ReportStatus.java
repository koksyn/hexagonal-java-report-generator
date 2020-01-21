package com.report.application.domain.vo;

import lombok.Getter;
import lombok.NonNull;

public class ReportStatus {
    @Getter
    private final com.report.application.domain.type.ReportStatus raw;

    public ReportStatus(@NonNull final com.report.application.domain.type.ReportStatus raw) {
        this.raw = raw;
    }
}