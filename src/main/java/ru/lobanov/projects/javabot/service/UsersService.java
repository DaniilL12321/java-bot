package ru.lobanov.projects.javabot.service;


import ru.lobanov.projects.javabot.model.Jokes;
import ru.lobanov.projects.javabot.model.Users;

import java.util.AbstractMap;
import java.util.List;

public interface UsersService {
    // Вывод всех пользователей
    List<Users> allUsers();
}
