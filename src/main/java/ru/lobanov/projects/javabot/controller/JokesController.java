package ru.lobanov.projects.javabot.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lobanov.projects.javabot.model.Jokes;
import ru.lobanov.projects.javabot.service.JokesService;

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
    public List<Jokes> allJokes() {
        return service.allJokes();
    }
    // GET /jokes/id - выдача шутки с определенном ID
    @GetMapping("/{id}")
    ResponseEntity<Jokes> getJokes(@PathVariable("id") Long id) {
        return service.getJokesById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    // POST /jokes - создание новой шутки
    @PostMapping
    public ResponseEntity<Jokes> addNewJoke(@RequestBody Jokes newJoke) {
        Optional<Jokes> savedJoke = service.addNewJoke(newJoke);
        return savedJoke.map(jokes -> ResponseEntity.status(HttpStatus.CREATED).body(jokes))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
    // PUT /jokes/id - изменение шутки
    @PutMapping("/{id}")
    public ResponseEntity<Jokes> updateJoke(@PathVariable Long id, @RequestBody Jokes updatedJoke) {
        Optional<Jokes> savedJoke = service.updateJoke(id, updatedJoke);

        return savedJoke.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
