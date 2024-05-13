package ru.lobanov.projects.javabot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @SequenceGenerator(name = "jokes_id_seq", sequenceName = "jokes_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "shutka")
    private String shutka;

    @Column(name = "time_created")
    private Date timeCreated;

    @Column(name = "time_updated")
    private Date timeUpdated;

    @OneToMany(mappedBy = "jokes", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Users> users = new ArrayList<>();

    public Jokes(String shutka) {
        this.shutka = shutka;
    }
}