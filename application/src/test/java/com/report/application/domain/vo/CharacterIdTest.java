package com.report.application.domain.vo;

import com.report.common.unit.vo.UnsignedLongIdTest;

class CharacterIdTest extends UnsignedLongIdTest<CharacterId> {
    @Override
    protected Class<CharacterId> getClassOfTheInstanceBeingCreated() {
        return CharacterId.class;
    }
}
