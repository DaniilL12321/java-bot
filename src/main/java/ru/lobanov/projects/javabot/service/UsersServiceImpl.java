package ru.lobanov.projects.javabot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.stereotype.Service;
import ru.lobanov.projects.javabot.model.Jokes;
import ru.lobanov.projects.javabot.model.Users;
import ru.lobanov.projects.javabot.repository.JokesRepository;
import ru.lobanov.projects.javabot.repository.UsersRepository;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@SpringBootConfiguration
public class UsersServiceImpl implements UsersService {
    @Autowired
    private final UsersRepository usersRepository;
    @Autowired
    private final JokesRepository jokesRepository;


    // Вывод всех пользователей
    @Override
    public List<Users> allUsers() {
        List<Users> usersList = usersRepository.findAll();
        return usersList;
    }

    // топ шуток
    public List<AbstractMap.SimpleEntry<Jokes, Long>> topJokes() {
        return usersRepository.findTopJokes().stream()
                .map(obj -> new AbstractMap.SimpleEntry<>(
                        new Jokes((String) obj[0]), ((Number) obj[1]).longValue()))
                .collect(Collectors.toList());
    }
}
