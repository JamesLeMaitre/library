package dev.jtm.library.repositories;

import dev.jtm.library.entities.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentRepository extends JpaRepository<Incident,Long> {
}
