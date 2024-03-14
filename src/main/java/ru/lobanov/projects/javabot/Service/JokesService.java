package ru.lobanov.projects.javabot.Service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.lobanov.projects.javabot.Model.Jokes;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;

public interface JokesService {
    List<Jokes> AllJokes();
    Optional<Jokes> getJokesById(Long id);
    List<Jokes> getAllJokes();
    Optional<Jokes> addNewJoke(String json);
    boolean existsJokesById(Long id);
    void deleteJokesById(Long id);
    Jokes updateJoke(Jokes jokeToUpdate);
}
