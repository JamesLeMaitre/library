package dev.jtm.library.services;

import dev.jtm.library.entities.Incident;

import java.util.List;

public interface IncidentService {
    List<Incident> getAll();
    Incident create(Incident incident,Long idDocument);
    void delete(Long id);
    Incident edit(Incident incident,Long id);
    Incident getById(Long id);
    int getCountAll();
}
