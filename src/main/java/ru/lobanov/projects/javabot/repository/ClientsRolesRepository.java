package ru.lobanov.projects.javabot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.lobanov.projects.javabot.model.ClientsRole;

public interface ClientsRolesRepository extends CrudRepository<ClientsRole, Long> {
}
