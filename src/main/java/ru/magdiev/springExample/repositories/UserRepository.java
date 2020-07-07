package ru.magdiev.springExample.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.magdiev.springExample.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByActivationCode(String code);
}
