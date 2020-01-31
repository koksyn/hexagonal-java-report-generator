package com.report.application.domain.vo;

import com.report.common.unit.vo.NonBlankNameTest;

class PlanetNameTest extends NonBlankNameTest<PlanetName> {
    @Override
    protected Class<PlanetName> getClassOfTheInstanceBeingCreated() {
        return PlanetName.class;
    }
}
