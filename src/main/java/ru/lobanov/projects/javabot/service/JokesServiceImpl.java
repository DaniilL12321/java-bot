package ru.lobanov.projects.javabot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.lobanov.projects.javabot.model.Jokes;
import ru.lobanov.projects.javabot.repository.JokesRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public Jokes getRandomJoke() {
        int page = 0;
        int size = 11; // все шутки из БД
        List<Jokes> jokes = allJokes(PageRequest.of(page, size));

        if (jokes != null && !jokes.isEmpty()) {
            int randomIndex = (int) (Math.random() * jokes.size());
            return jokes.get(randomIndex);
        } else {
            return null;
        }
    }

    public boolean existsJokesById(Long id) {
        return jokesRepository.existsById(id);
    }

    public void deleteJokesById(Long id) {
        jokesRepository.deleteById(id);
    }
}