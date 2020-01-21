package com.report.application.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FilmCharacter {
    @Id
    private UUID uuid;

    @PrePersist
    private void prePersist() {
        uuid = UUID.randomUUID();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private Report report;

    @Column(nullable = false)
    private Long filmId;

    @Column(nullable = false)
    private String filmName;

    @Column(nullable = false)
    private Long characterId;

    @Column(nullable = false)
    private String characterName;

    @Column(nullable = false)
    private Long planetId;

    @Column(nullable = false)
    private String planetName;
}
