package dev.jtm.library.repositories;

import dev.jtm.library.entities.Nature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NatureRepository extends JpaRepository<Nature,Long> {
}
