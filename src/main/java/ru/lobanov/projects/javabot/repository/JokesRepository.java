package ru.lobanov.projects.javabot.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lobanov.projects.javabot.model.Jokes;

@Repository
public interface JokesRepository extends JpaRepository<Jokes, Long> {
}
