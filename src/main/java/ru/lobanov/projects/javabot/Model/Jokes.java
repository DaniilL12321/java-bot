package ru.lobanov.projects.javabot.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity(name = "Jokes")
@Table(name = "Jokes")
public class Jokes {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "shutka")
    private String shutka;

    @Column(name = "time_created")
    private LocalDate timeCreated;

    @Column(name = "time_updated")
    private LocalDate timeUpdated;
}