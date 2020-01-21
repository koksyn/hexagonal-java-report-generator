package com.report.application.domain.vo;

import com.report.common.unit.vo.NonBlankNameTest;

class CharacterNameTest extends NonBlankNameTest<CharacterName> {
    @Override
    protected Class<CharacterName> getClassOfTheInstanceBeingCreated() {
        return CharacterName.class;
    }
}
