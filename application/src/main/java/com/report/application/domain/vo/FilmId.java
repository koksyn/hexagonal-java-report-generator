package com.report.application.domain.vo;

import com.google.common.primitives.UnsignedLong;
import com.report.common.vo.UnsignedLongId;

public class FilmId extends UnsignedLongId {
    public FilmId(final UnsignedLong raw) {
        super(raw);
    }
}