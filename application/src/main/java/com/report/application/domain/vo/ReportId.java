package com.report.application.domain.vo;

import com.report.common.vo.UniqueId;

import java.util.UUID;

public class ReportId extends UniqueId {
    public ReportId() {
        super();
    }

    public ReportId(final UUID raw) {
        super(raw);
    }
}