package com.report.common.vo;

import com.report.common.exception.ReportException;
import lombok.Getter;

public class PositiveInteger {
    @Getter
    private final int raw;

    public PositiveInteger(final int raw) {
        if(raw <= 0) {
            throw new ReportException("Number should be a positive integer.");
        }

        this.raw = raw;
    }
}
