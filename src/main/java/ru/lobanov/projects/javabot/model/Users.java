package ru.lobanov.projects.javabot.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity(name = "Users")
@Table(name = "Users")
public class Users {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "time_watched")
    private Date timeWatched;

    @Column(name = "jokes_id")
    private long jokesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jokes_id", insertable = false, updatable = false)
    private Jokes jokes;
}