package dev.jtm.library.repositories;

import dev.jtm.library.entities.Archives;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveRepository extends JpaRepository<Archives,Long> {
}
