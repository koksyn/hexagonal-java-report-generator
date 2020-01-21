package com.report.application.domain.vo;

import com.report.common.unit.vo.UnsignedLongIdTest;

class FilmIdTest extends UnsignedLongIdTest<FilmId> {
    @Override
    protected Class<FilmId> getClassOfTheInstanceBeingCreated() {
        return FilmId.class;
    }
}
