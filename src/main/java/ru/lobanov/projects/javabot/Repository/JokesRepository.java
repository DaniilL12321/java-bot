package ru.lobanov.projects.javabot.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lobanov.projects.javabot.Model.Jokes;

@Repository
public interface JokesRepository extends JpaRepository<Jokes, Long> {
    List<Jokes> getJokesBy();
}
