package ru.lobanov.projects.javabot.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lobanov.projects.javabot.Model.Jokes;
import ru.lobanov.projects.javabot.Repository.JokesRepository;

import java.io.IOException;
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
    public List<Jokes> AllJokes() {
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
    public Optional<Jokes> addNewJoke(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Jokes newJoke = objectMapper.readValue(json, Jokes.class);

            newJoke.setTimeCreated(LocalDate.now());
            newJoke.setTimeUpdated(LocalDate.now());

            Jokes savedJoke = jokesRepository.save(newJoke);

            return Optional.of(savedJoke);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
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
            throw new NoSuchElementException("Joke with ID " + updatedJoke.getId() + " not found");
        }
    }
    public boolean existsJokesById(Long id) {
        return jokesRepository.existsById(id);
    }
    public void deleteJokesById(Long id) {
        jokesRepository.deleteById(id);
    }
}
