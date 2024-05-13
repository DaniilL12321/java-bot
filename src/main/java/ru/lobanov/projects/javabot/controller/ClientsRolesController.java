package ru.lobanov.projects.javabot.controller;

import org.springframework.web.bind.annotation.*;
import ru.lobanov.projects.javabot.model.ClientsRole;
import ru.lobanov.projects.javabot.service.ClientsService;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class ClientsRolesController {
    private final ClientsService clientsService;
    
    public ClientsRolesController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping()
    public List<ClientsRole> getClientsRoles() {
        return clientsService.getClientsRoles();
    }

    @GetMapping("/{clientId}")
    public List<ClientsRole> getClientRoles(@PathVariable Long clientId) {
        return clientsService.getClientRoles(clientId);
    }

    @PutMapping("/{clientId}")
    public void updateClientRoles(@PathVariable Long clientId, @RequestBody List<ClientsRole> newRoles) {
        clientsService.updateClientRoles(clientId, newRoles);
    }
}