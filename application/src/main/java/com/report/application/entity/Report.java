package com.report.application.entity;

import com.report.application.domain.type.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.jdo.annotations.Index;
import javax.jdo.annotations.Indices;
import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Indices(@Index(members={"reportId","status"}))
public class Report {
    @Id
    @Column(columnDefinition = "uuid", nullable = false, updatable = false)
    private UUID reportId;

    @Column(nullable = false)
    private String characterPhrase;

    @Column(nullable = false)
    private String planetName;

    @Index
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status;

    @OneToMany(
        fetch = FetchType.EAGER,
        mappedBy = "report",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<FilmCharacter> filmCharacters;
}