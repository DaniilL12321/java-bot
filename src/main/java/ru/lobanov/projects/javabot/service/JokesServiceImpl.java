package ru.lobanov.projects.javabot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lobanov.projects.javabot.model.Jokes;
import ru.lobanov.projects.javabot.repository.JokesRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JokesServiceImpl implements JokesService{
    private final JokesRepository jokesRepository;

    LocalDate currentDate = LocalDate.now();
    // Вывод всех шуток
    @Override
    public List<Jokes> allJokes() {
        List<Jokes> jokesList = jokesRepository.findAll();

        return jokesList;
    }
    // Вывод шутки и информации о ней по ID
    public Optional<Jokes> getJokesById(Long id) {
        return jokesRepository.findById(id);
    }
    @Override
    public List<Jokes> getAllJokes() {
        return jokesRepository.getJokesBy();
    }
    // Добавление новой шутки
    @Override
    public Optional<Jokes> addNewJoke(Jokes newJoke) {
        try {
            newJoke.setTimeCreated(LocalDate.now());
            newJoke.setTimeUpdated(LocalDate.now());

            Jokes savedJoke = jokesRepository.save(newJoke);

            return Optional.of(savedJoke);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    // Изменение шутки
    public Jokes updateJoke(Jokes updatedJoke) {
        Optional<Jokes> existingJoke = jokesRepository.findById(updatedJoke.getId());

        if (existingJoke.isPresent()) {
            Jokes jokeToUpdate = existingJoke.get();
            jokeToUpdate.setShutka(updatedJoke.getShutka());
            jokeToUpdate.setTimeUpdated(LocalDate.now());

            return jokesRepository.save(jokeToUpdate);
        } else {
            throw new NoSuchElementException("Шутка с ID " + updatedJoke.getId() + " не найдена");
        }
    }
    public boolean existsJokesById(Long id) {
        return jokesRepository.existsById(id);
    }
    public void deleteJokesById(Long id) {
        jokesRepository.deleteById(id);
    }
}