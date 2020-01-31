package com.report.application.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Film implements SwapiEntity {
    @Id
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ElementCollection
    @CollectionTable(name = "film_character_id", joinColumns = @JoinColumn(name = "film_id"))
    @Column(name = "character_id")
    private Set<Long> charactersIds = new HashSet<>();
}
