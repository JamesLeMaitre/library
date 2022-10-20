package dev.jtm.library.repositories.security;

import dev.jtm.library.entities.security.AppUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUsersRepository extends JpaRepository<AppUsers,Long> {
    Optional<AppUsers> findByUsername(String username);
    Optional<AppUsers> findByPassword(String password);
    Optional<AppUsers> findByEmail(String email);
}
