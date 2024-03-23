package ru.lobanov.projects.javabot.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.lobanov.projects.javabot.model.Jokes;
import ru.lobanov.projects.javabot.service.JokesService;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest
class JokesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JokesService jokesService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Add new joke")
    @Test
    void addNewJoke() throws Exception {
        Jokes newJoke = new Jokes(1L, "Joke text", new Date(), new Date());
        Mockito.when(jokesService.addNewJoke(Mockito.any(Jokes.class))).thenReturn(Optional.of(newJoke));

        String response = mockMvc.perform(
                        MockMvcRequestBuilders.post("/jokes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newJoke))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString();

        JsonNode responseJson = objectMapper.readTree(response);
        JsonNode expectedJson = objectMapper.readTree(objectMapper.writeValueAsString(newJoke));

        assertEquals(expectedJson.get("id"), responseJson.get("id"));
        assertEquals(expectedJson.get("shutka"), responseJson.get("shutka"));
    }

    @DisplayName("Get all jokes")
    @Test
    void allJokes() throws Exception {
        Jokes savedJoke = new Jokes(1L, "Joke text", new Date(), new Date());
        Mockito.when(jokesService.allJokes()).thenReturn(Collections.singletonList(savedJoke));

        String response = mockMvc.perform(
                        MockMvcRequestBuilders.get("/jokes")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonNode responseJson = objectMapper.readTree(response);
        JsonNode expectedJson = objectMapper.readTree(objectMapper.writeValueAsString(Collections.singletonList(savedJoke)));

        assertEquals(expectedJson.get(0).get("id"), responseJson.get(0).get("id"));
        assertEquals(expectedJson.get(0).get("shutka"), responseJson.get(0).get("shutka"));
    }

    @DisplayName("Get joke by ID")
    @Test
    void getJokes() throws Exception {
        Jokes savedJoke = new Jokes(1L, "Joke text", new Date(), new Date());
        Mockito.when(jokesService.getJokesById(1L)).thenReturn(Optional.of(savedJoke));

        String response = mockMvc.perform(
                        MockMvcRequestBuilders.get("/jokes/1")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonNode responseJson = objectMapper.readTree(response);
        JsonNode expectedJson = objectMapper.readTree(objectMapper.writeValueAsString(savedJoke));

        assertEquals(expectedJson.get("id"), responseJson.get("id"));
        assertEquals(expectedJson.get("shutka"), responseJson.get("shutka"));
    }

    @DisplayName("Update joke")
    @Test
    void updateJoke() throws Exception {
        Jokes updatedJoke = new Jokes(1L, "Updated joke text", new Date(), new Date());
        Mockito.when(jokesService.updateJoke(Mockito.anyLong(), Mockito.any(Jokes.class))).thenReturn(Optional.of(updatedJoke));

        String response = mockMvc.perform(
                        MockMvcRequestBuilders.put("/jokes/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updatedJoke))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonNode responseJson = objectMapper.readTree(response);
        JsonNode expectedJson = objectMapper.readTree(objectMapper.writeValueAsString(updatedJoke));

        assertEquals(expectedJson.get("id"), responseJson.get("id"));
        assertEquals(expectedJson.get("shutka"), responseJson.get("shutka"));
    }

    @DisplayName("Delete joke")
    @Test
    void delJokes() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/jokes/1")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}