package ru.lobanov.projects.javabot.service;

import ru.lobanov.projects.javabot.model.ClientsRole;

import java.util.List;

public interface ClientsService {
    void registration(String username, String password);

    // Все роли клиентов
    List<ClientsRole> getClientsRoles();

    List<ClientsRole> getClientRoles(Long clientId);
    void updateClientRoles(Long clientId, List<ClientsRole> newRoles);
}