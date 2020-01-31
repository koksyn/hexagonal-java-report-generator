package com.report.application.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Character implements SwapiEntity {
    @Id
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;
}
