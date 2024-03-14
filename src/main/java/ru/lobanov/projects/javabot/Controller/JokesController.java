package ru.lobanov.projects.javabot.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lobanov.projects.javabot.Model.Jokes;
import ru.lobanov.projects.javabot.Service.JokesService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("jokes")
@AllArgsConstructor
public class JokesController {
    private final JokesService service;
    // GET /jokes - выдача всех шуток
    @GetMapping
    public List<Jokes> AllJokes() {
        return service.AllJokes();
    }
    // GET /jokes/id - выдача шутки с определенном ID
    @GetMapping("/{id}")
    ResponseEntity<Jokes> getJokes(@PathVariable("id") Long id) {
        return service.getJokesById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    // POST /jokes - создание новой шутки
    @PostMapping
    public ResponseEntity<Jokes> addNewJoke(@RequestBody String json) {
        Optional<Jokes> newJoke = service.addNewJoke(json);
        return newJoke.map(jokes -> ResponseEntity.status(HttpStatus.CREATED).body(jokes))
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
    // PUT /jokes/id - изменение шутки
    @PutMapping("/{id}")
    public ResponseEntity<Jokes> updateJoke(@PathVariable Long id, @RequestBody Jokes updatedJoke) {
        Optional<Jokes> existingJoke = service.getJokesById(id);

        if (existingJoke.isPresent()) {
            Jokes jokeToUpdate = existingJoke.get();
            jokeToUpdate.setShutka(updatedJoke.getShutka());
            jokeToUpdate.setTimeUpdated(LocalDate.now());

            Jokes savedJoke = service.updateJoke(jokeToUpdate);

            return ResponseEntity.ok(savedJoke);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
    // DELETE /jokes/id - удаление шутки
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delJokes(@PathVariable("id") Long id) {
        if (service.existsJokesById(id)) {
            service.deleteJokesById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
