package com.report.application.domain.vo;

import com.report.common.unit.vo.NonBlankNameTest;

class FilmNameTest extends NonBlankNameTest<FilmName> {
    @Override
    protected Class<FilmName> getClassOfTheInstanceBeingCreated() {
        return FilmName.class;
    }
}
