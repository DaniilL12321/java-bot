package ru.lobanov.projects.javabot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.lobanov.projects.javabot.model.Jokes;
import ru.lobanov.projects.javabot.model.Users;

import java.util.AbstractMap;
import java.util.List;

public interface UsersService {
    // Вывод всех пользователей
    List<Users> allUsers();

    List<AbstractMap.SimpleEntry<Jokes, Long>> topJokes();
}
