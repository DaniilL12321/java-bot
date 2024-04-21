package ru.lobanov.projects.javabot.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.lobanov.projects.javabot.exceptions.UsernameAlreadyExistsException;
import ru.lobanov.projects.javabot.model.Clients;
import ru.lobanov.projects.javabot.model.ClientsAuthority;
import ru.lobanov.projects.javabot.model.ClientsRole;
import ru.lobanov.projects.javabot.repository.ClientsRepository;
import ru.lobanov.projects.javabot.repository.ClientsRolesRepository;

@RequiredArgsConstructor
@Service
public class ClientsServiceImpl implements ClientsService, UserDetailsService {

    private final ClientsRolesRepository clientsRolesRepository;
    private final ClientsRepository clientsRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
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
            clientsRolesRepository.save(new ClientsRole(null, ClientsAuthority.PLACE_JOKES, user));
        }
        else {
            throw new UsernameAlreadyExistsException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return clientsRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}