package dev.jtm.library.services;

import dev.jtm.library.entities.Archives;

import java.util.List;

public interface ArchivesService {
    List<Archives> getAll();
    Archives create(Archives archives,Long idRayons );
    void delete(Long id);
    Archives edit(Archives archives,Long id);
    Archives getById(Long id);
    int getCountAll();
}
