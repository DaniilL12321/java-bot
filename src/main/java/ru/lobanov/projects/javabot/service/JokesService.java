package ru.lobanov.projects.javabot.service;

import ru.lobanov.projects.javabot.model.Jokes;

import java.util.List;
import java.util.Optional;

public interface JokesService {
    List<Jokes> allJokes();
    Optional<Jokes> getJokesById(Long id);
    List<Jokes> getAllJokes();
    boolean existsJokesById(Long id);
    void deleteJokesById(Long id);
    // Добавление новой шутки
    Optional<Jokes> addNewJoke(Jokes newJoke);
    Jokes updateJoke(Jokes jokeToUpdate);
}
