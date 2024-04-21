package ru.lobanov.projects.javabot.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import ru.lobanov.projects.javabot.model.Clients;

public interface ClientsRepository extends CrudRepository<Clients, Long> {
    Optional<Clients> findByUsername(String username);
}