package com.report.application.port.driven;

import com.report.application.entity.Planet;
import java.util.List;

public interface PlanetRepository {
    void saveAll(List<Planet> planets);
}
