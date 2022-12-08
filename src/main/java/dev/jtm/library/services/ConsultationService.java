package dev.jtm.library.services;

import dev.jtm.library.entities.Consultation;


import java.util.List;

public interface ConsultationService {
    List<Consultation> getAll();
    Consultation create(Consultation consultation,Long idDocument );
    void delete(Long id);
    Consultation edit(Consultation consultation,Long id);
    Consultation getById(Long id);
    int getCountAll();
}
