package ru.lobanov.projects.javabot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.lobanov.projects.javabot.model.Jokes;
import ru.lobanov.projects.javabot.repository.JokesRepository;

import java.util.AbstractMap;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@SpringBootConfiguration
public class JokesServiceImpl implements JokesService{
    private final JokesRepository jokesRepository;

    // Вывод всех шуток
    @Override
    public List<Jokes> allJokes(PageRequest pageRequest) {
        Page<Jokes> page = jokesRepository.findAll(pageRequest);

        return page.getContent();
    }

    // Вывод шутки и информации о ней по ID
    public Optional<Jokes> getJokesById(Long id) {
        return jokesRepository.findById(id);
    }

    // Добавление новой шутки
    @Override
    public Optional<Jokes> addNewJoke(Jokes newJoke) {
        try {
            newJoke.setTimeCreated(new Date());
            newJoke.setTimeUpdated(new Date());

            Jokes savedJoke = jokesRepository.save(newJoke);

            return Optional.of(savedJoke);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    // Изменение шутки
    public Optional<Jokes> updateJoke(Long id, Jokes updatedJoke) {
        Optional<Jokes> existingJoke = jokesRepository.findById(id);

        if (existingJoke.isPresent()) {
            Jokes jokeToUpdate = existingJoke.get();
            jokeToUpdate.setShutka(updatedJoke.getShutka());
            jokeToUpdate.setTimeUpdated(new Date());

            return Optional.of(jokesRepository.save(jokeToUpdate));
        } else {
            return Optional.empty();
        }
    }

    // полноценный рандом с БД
    public Jokes getRandomJoke() {
        return jokesRepository.getRandomJoke();
    }

    // топ шуток
    public List<AbstractMap.SimpleEntry<Jokes, Long>> topJokes() {
        return jokesRepository.findTopJokes().stream()
                .map(obj -> new AbstractMap.SimpleEntry<>(
                        new Jokes((String) obj[0]), ((Number) obj[1]).longValue()))
                .collect(Collectors.toList());
    }

    public boolean existsJokesById(Long id) {
        return jokesRepository.existsById(id);
    }

    public void deleteJokesById(Long id) {
        jokesRepository.deleteById(id);
    }
}