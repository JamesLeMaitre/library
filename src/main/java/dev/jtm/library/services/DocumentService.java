package dev.jtm.library.services;

import dev.jtm.library.entities.Documents;

import java.util.List;

public interface DocumentService {
    List<Documents> getAll();
    Documents create(Documents document,Long idNature,Long idRayons );
    void delete(Long id);
    Documents edit(Documents document,Long id);
    Documents getById(Long id);
    int getCountAll();
}
