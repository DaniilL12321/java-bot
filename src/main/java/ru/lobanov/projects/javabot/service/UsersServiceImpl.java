package ru.lobanov.projects.javabot.service;

import lombok.RequiredArgsConstructor;
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
    private final UsersRepository usersRepository;
    private final JokesRepository jokesRepository;


    // Вывод всех пользователей
    @Override
    public List<Users> allUsers() {
        List<Users> usersList = usersRepository.findAll();
        return usersList;
    }

    @Override
    public List<AbstractMap.SimpleEntry<Jokes, Long>> topJokes(int number) {
        return usersRepository.findAll().stream()
                .collect(Collectors.groupingBy(Users::getJokesId, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(number)
                .map(entry -> new AbstractMap.SimpleEntry<>(
                        jokesRepository.findById(entry.getKey()).orElse(null), entry.getValue()))
                .collect(Collectors.toList()).reversed();
    }
}