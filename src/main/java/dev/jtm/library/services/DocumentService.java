package dev.jtm.library.services;

import dev.jtm.library.entities.Document;

import java.util.List;

public interface DocumentService {
    List<Document> getAll();
    Document create(Document document,Long idNature,Long idRayons );
    void delete(Long id);
    Document edit(Document document,Long id);
    Document getById(Long id);
    int getCountAll();
}
