package ru.lobanov.projects.javabot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.lobanov.projects.javabot.model.Jokes;

import java.util.List;

@Repository
public interface JokesRepository extends JpaRepository<Jokes, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM jokes ORDER BY RAND() LIMIT 1")
    Jokes getRandomJoke();

    @Query(value = "SELECT j.shutka, COUNT(u.jokes_id) as jokeCount " +
            "FROM Users u " +
            "JOIN Jokes j ON u.jokes_id = j.id " +
            "GROUP BY j.shutka " +
            "ORDER BY jokeCount DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findTopJokes();
}
