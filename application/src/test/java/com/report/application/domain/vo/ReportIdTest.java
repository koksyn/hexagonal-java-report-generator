package com.report.application.domain.vo;

import com.report.common.unit.vo.UniqueIdTest;

class ReportIdTest extends UniqueIdTest<ReportId> {
    @Override
    protected Class<ReportId> getClassOfTheInstanceBeingCreated() {
        return ReportId.class;
    }
}
