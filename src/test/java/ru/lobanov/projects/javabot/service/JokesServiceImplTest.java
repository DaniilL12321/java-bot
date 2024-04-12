package ru.lobanov.projects.javabot.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.lobanov.projects.javabot.model.Jokes;
import ru.lobanov.projects.javabot.repository.JokesRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class JokesServiceImplTest {
    @Autowired
    private JokesServiceImpl jokesService;
    @MockBean
    private JokesRepository jokesRepository;
    private Jokes jokes;

    @Test
    @DisplayName("Получение всех шуток")
    void allJokes() {
        Jokes joke1 = Jokes.builder()
                .id(1L)
                .shutka("Шутка 1")
                .timeCreated(new Date())
                .timeUpdated(new Date())
                .build();

        Jokes joke2 = Jokes.builder()
                .id(2L)
                .shutka("Шутка 2")
                .timeCreated(new Date())
                .timeUpdated(new Date())
                .build();

        Mockito.when(jokesRepository.findAll()).thenReturn(List.of(joke1, joke2));
        assertEquals(2, jokesService.allJokes(PageRequest.of(page, size)).size());
    }

    @Test
    @DisplayName("Получение шутки по ID")
    void getJokesById() {
        jokes = Jokes.builder()
                .id(1L).build();
        Optional<Jokes> jokesOptional = Optional.of(jokes);
        Mockito.when(jokesRepository.findById(1L)).thenReturn(jokesOptional);
        assertEquals(jokesOptional, jokesService.getJokesById(1L));
    }

    @Test
    @DisplayName("Добавление новой шутки")
    void addNewJoke() {
        jokes = Jokes.builder()
                .id(1L)
                .shutka("Шутка 1")
                .timeCreated(new Date())
                .timeUpdated(new Date())
                .build();
        Mockito.when(jokesRepository.save(jokes)).thenReturn(jokes);
        assertEquals(Optional.of(jokes), jokesService.addNewJoke(jokes));
    }

    @Test
    @DisplayName("Изменение шутки")
    void updateJoke() {
        jokes = Jokes.builder()
                .id(1L)
                .shutka("Шутка 1")
                .timeCreated(new Date())
                .timeUpdated(new Date())
                .build();
        Optional<Jokes> jokesOptional = Optional.of(jokes);
        Mockito.when(jokesRepository.findById(1L)).thenReturn(jokesOptional);
        Mockito.when(jokesRepository.save(jokes)).thenReturn(jokes);
        assertEquals(Optional.of(jokes), jokesService.updateJoke(1L, jokes));
    }
}