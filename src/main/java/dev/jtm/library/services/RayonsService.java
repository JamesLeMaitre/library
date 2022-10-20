package dev.jtm.library.services;

import dev.jtm.library.entities.Rayons;

import java.util.Collection;
import java.util.List;

public interface RayonsService {
    List<Rayons> getAll();
    Rayons create(Rayons rayons );
    void delete(Long id);
    Rayons edit(Rayons rayons,Long id);
    Rayons getById(Long id);
    int getCountAll();
}
