package dev.jtm.library.repositories.security;

import dev.jtm.library.entities.security.AppUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AppUsersRepository extends JpaRepository<AppUsers,Long> {
    Optional<AppUsers> findByUsername(String username);
    Optional<AppUsers> findByPassword(String password);
    Optional<AppUsers> findByEmail(String email);

    @Query("SELECT u FROM AppUsers u WHERE u.username= :username")
    AppUsers getCurrentUser(@Param("username") String username);
}
