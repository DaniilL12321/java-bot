package ru.lobanov.projects.javabot.service;

import org.springframework.data.domain.PageRequest;
import ru.lobanov.projects.javabot.model.Jokes;

import java.util.List;
import java.util.Optional;

public interface JokesService {
    List<Jokes> allJokes(PageRequest pageRequest);
    Optional<Jokes> getJokesById(Long id);
    boolean existsJokesById(Long id);
    void deleteJokesById(Long id);
    // Добавление новой шутки
    Optional<Jokes> addNewJoke(Jokes newJoke);
    Optional<Jokes> updateJoke(Long id, Jokes updatedJoke);
    Jokes getRandomJoke();
}