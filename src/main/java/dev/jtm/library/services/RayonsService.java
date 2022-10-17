package dev.jtm.library.services;

import dev.jtm.library.entities.Rayons;

import java.util.Collection;

public interface RayonsService {
    Collection<Rayons> getAll();
    Rayons save(Rayons rayons);
    void delete(Long id);
    Rayons edit(Rayons rayons,Long id);
    Rayons getById(Long id);
    int getCountAll();
}
