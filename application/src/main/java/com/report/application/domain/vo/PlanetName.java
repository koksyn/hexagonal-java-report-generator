package com.report.application.domain.vo;

import com.report.common.vo.NonBlankName;
import lombok.NonNull;

public class PlanetName extends NonBlankName {
    public PlanetName(final String raw) {
        super(raw);
    }

    public boolean differ(@NonNull PlanetName planetName) {
        return !getRaw().equals(planetName.getRaw());
    }
}