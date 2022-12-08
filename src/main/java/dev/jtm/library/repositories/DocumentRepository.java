package dev.jtm.library.repositories;


import dev.jtm.library.entities.Documents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Documents,Long> {
}
