package dev.jtm.library.services;

import dev.jtm.library.entities.Nature;
import dev.jtm.library.entities.Rayons;

import java.util.List;

public interface NatureService {
    List<Nature> getAll();
    Nature create(Nature nature );
    void delete(Long id);
    Nature edit(Nature nature,Long id);
    Nature getById(Long id);
    int getCountAll();
}
