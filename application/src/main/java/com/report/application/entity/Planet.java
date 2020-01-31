package com.report.application.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.jdo.annotations.Index;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Planet implements SwapiEntity {
    @Id
    @Column(nullable = false)
    private Long id;

    @Index
    @Column(nullable = false)
    private String name;

    @ElementCollection
    @CollectionTable(name = "planet_film_id", joinColumns = @JoinColumn(name = "planet_id"))
    @Column(name = "film_id")
    private Set<Long> filmIds = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "planet_character_id", joinColumns = @JoinColumn(name = "planet_id"))
    @Column(name = "character_id")
    private Set<Long> characterIds = new HashSet<>();
}
