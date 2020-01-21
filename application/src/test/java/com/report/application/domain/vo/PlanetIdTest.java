package com.report.application.domain.vo;

import com.report.common.unit.vo.UnsignedLongIdTest;

class PlanetIdTest extends UnsignedLongIdTest<PlanetId> {
    @Override
    protected Class<PlanetId> getClassOfTheInstanceBeingCreated() {
        return PlanetId.class;
    }
}
