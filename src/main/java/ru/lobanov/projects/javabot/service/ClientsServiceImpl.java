package ru.lobanov.projects.javabot.service;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetailsService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.lobanov.projects.javabot.exceptions.UsernameAlreadyExistsException;
import ru.lobanov.projects.javabot.model.Clients;
import ru.lobanov.projects.javabot.model.ClientsAuthority;
import ru.lobanov.projects.javabot.model.ClientsRole;
import ru.lobanov.projects.javabot.repository.ClientsRepository;
import ru.lobanov.projects.javabot.repository.ClientsRolesRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientsServiceImpl implements ClientsService, UserDetailsService {
    private final ClientsRolesRepository clientsRolesRepository;
    private final ClientsRepository clientsRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void registration(String username, String password) {
        if (clientsRepository.findByUsername(username).isEmpty()) {
            Clients user = clientsRepository.save(
                    new Clients()
                            .setId(null)
                            .setUsername(username)
                            .setPassword(passwordEncoder.encode(password))
                            .setLocked(false)
                            .setExpired(false)
                            .setEnabled(true)
            );
            clientsRolesRepository.save(new ClientsRole(null, ClientsAuthority.VIEW_JOKES, user));
        }
        else {
            throw new UsernameAlreadyExistsException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return clientsRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    // Все роли клиентов
    @Override
    public List<ClientsRole> getClientsRoles() {
        return (List<ClientsRole>) clientsRolesRepository.findAll();
    }

    @Override
    public List<ClientsRole> getClientRoles(Long clientId) {
        Clients client = clientsRepository.findById(clientId)
                .orElseThrow(() -> new UsernameNotFoundException("Client not found"));
        return client.getClientsRoles();
    }

    // изменение роли клиента в clientsRolesRepository
    @Override
    public void updateClientRoles(Long clientId, List<ClientsRole> newRoles) {
        Clients client = clientsRepository.findById(clientId)
                .orElseThrow(() -> new UsernameNotFoundException("Client not found"));
        clientsRolesRepository.deleteAll(client.getClientsRoles());
        newRoles.forEach(role -> role.setClients(client));
        clientsRolesRepository.saveAll(newRoles);
    }
}