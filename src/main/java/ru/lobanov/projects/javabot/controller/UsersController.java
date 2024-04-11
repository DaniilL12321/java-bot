package ru.lobanov.projects.javabot.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lobanov.projects.javabot.model.Jokes;
import ru.lobanov.projects.javabot.model.Users;
import ru.lobanov.projects.javabot.service.UsersService;

import java.util.AbstractMap;
import java.util.List;

@RestController
@AllArgsConstructor
public class UsersController {
    private final UsersService usersService;

    // Вывод пользователей запрашивавших шутки
    @GetMapping("/users")
    public List<Users> allUsers() {
        return usersService.allUsers();
    }

    @GetMapping("/top/{number}")
    public List<AbstractMap.SimpleEntry<Jokes, Long>> topJokes(@PathVariable int number) {
        return usersService.topJokes(number);
    }
}