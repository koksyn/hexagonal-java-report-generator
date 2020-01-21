package com.report.application.domain.snapshot;

import com.report.application.domain.vo.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReportSnapshot {
    private final ReportId reportId;
    private final CharacterPhrase characterPhrase;
    private final PlanetName planetName;
    private final FilmCharacterList filmCharacters;
    private final ReportStatus reportStatus;
}
