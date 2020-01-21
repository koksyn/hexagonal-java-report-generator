package com.report.application.domain;

import com.report.application.domain.snapshot.ReportSnapshot;
import com.report.application.domain.type.ReportStatus;
import com.report.application.domain.vo.*;
import com.report.common.exception.LogicException;
import lombok.NonNull;

public class Report {
    private ReportId reportId;
    private CharacterPhrase characterPhrase;
    private PlanetName planetName;
    private FilmCharacterList filmCharacters;
    private ReportStatus reportStatus;

    public Report(
            @NonNull final ReportId reportId,
            @NonNull final CharacterPhrase characterPhrase,
            @NonNull final PlanetName planetName) {
        this.reportId = reportId;
        this.characterPhrase = characterPhrase;
        this.planetName = planetName;
        filmCharacters = new FilmCharacterList();
        reportStatus = ReportStatus.INCOMPLETE;
    }

    private Report(
            @NonNull final ReportId reportId,
            @NonNull final CharacterPhrase characterPhrase,
            @NonNull final PlanetName planetName,
            @NonNull final FilmCharacterList filmCharacters,
            @NonNull final com.report.application.domain.vo.ReportStatus immutableReportStatus) {
        this.reportId = reportId;
        this.characterPhrase = characterPhrase;
        this.planetName = planetName;
        this.filmCharacters = filmCharacters;
        reportStatus = immutableReportStatus.getRaw();
    }

    public ReportSnapshot toSnapshot() {
        com.report.application.domain.vo.ReportStatus immutableReportStatus =
                new com.report.application.domain.vo.ReportStatus(reportStatus);

        return new ReportSnapshot(reportId, characterPhrase, planetName, filmCharacters, immutableReportStatus);
    }

    public static Report fromSnapshot(@NonNull final ReportSnapshot snapshot) {
        return new Report(
                snapshot.getReportId(),
                snapshot.getCharacterPhrase(),
                snapshot.getPlanetName(),
                snapshot.getFilmCharacters(),
                snapshot.getReportStatus()
        );
    }

    public void prepareForProcessing(CharacterPhrase characterPhrase, PlanetName planetName) {
        if(this.planetName.differ(planetName)) {
            this.planetName = planetName;
        }

        if(this.characterPhrase.differ(characterPhrase)) {
            this.characterPhrase = characterPhrase;
        }

        reportStatus = ReportStatus.INCOMPLETE;
        filmCharacters.clearWhenNotEmpty();
    }

    public void markComplete() {
        if(ReportStatus.COMPLETE.equals(reportStatus)) {
            throw new LogicException("Cannot mark as complete, because report is complete already.");
        }

        reportStatus = ReportStatus.COMPLETE;
    }

    public void addFilmCharacter(@NonNull final FilmCharacter filmCharacter) {
        filmCharacters.add(filmCharacter);
    }
}
