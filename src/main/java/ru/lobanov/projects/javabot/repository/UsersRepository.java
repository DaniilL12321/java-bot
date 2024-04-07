package ru.lobanov.projects.javabot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lobanov.projects.javabot.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
}
