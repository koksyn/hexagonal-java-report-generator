package com.report.common.vo;

import com.google.common.base.CaseFormat;
import com.report.common.exception.ValidationException;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

public abstract class NonBlankName {
    @Getter
    private final String raw;

    public NonBlankName(@NonNull final String raw) {
        requireNonBlank(raw);

        this.raw = raw.trim();
    }

    private void requireNonBlank(String raw) {
        if(StringUtils.isBlank(raw)) {
            throw new ValidationException(
                    generateSnakeCaseClassName() + " should contain at least one character (not whitespace)."
            );
        }
    }

    private String generateSnakeCaseClassName() {
        String className = this.getClass()
                .getSimpleName();

        return CaseFormat.UPPER_CAMEL
                .to(CaseFormat.LOWER_UNDERSCORE, className);
    }
}
