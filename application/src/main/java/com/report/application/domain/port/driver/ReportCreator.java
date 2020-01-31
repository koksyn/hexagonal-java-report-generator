package com.report.application.domain.port.driver;

import com.report.application.domain.vo.CharacterPhrase;
import com.report.application.domain.vo.PlanetName;
import com.report.application.domain.vo.ReportId;

public interface ReportCreator {
    void createOrReplace(ReportId reportId, CharacterPhrase characterPhrase, PlanetName planetName);
}
