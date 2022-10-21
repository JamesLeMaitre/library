package dev.jtm.library.servicesimpl;

import dev.jtm.library.entities.Document;
import dev.jtm.library.entities.Incident;
import dev.jtm.library.repositories.DocumentRepository;
import dev.jtm.library.repositories.IncidentRepository;
import dev.jtm.library.services.IncidentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class IncidentServiceImpl implements IncidentService {
    private final IncidentRepository incidentRepository;
    private final DocumentRepository documentRepository;

    @Override
    public List<Incident> getAll() {
        return incidentRepository.findAll();
    }

    @Override
    public Incident create(Incident incident, Long idDocument) {
        // On recupère le ID De document
        Document document1 = documentRepository.findById(idDocument).orElse(null);
        // On set le id : le id est une clé étrangère dans la table INcident
        incident.setDocument(document1);
        // On enregistre l'opération
       return incidentRepository.save(incident);

    }

    @Override
    public void delete(Long id) {
        // On recupère le ID et on le supprime
        incidentRepository.deleteById(id);
    }

    @Override
    public Incident edit(Incident incident, Long id) {
        // On declare une nouveau incident
        Incident incident1 = incidentRepository.findById(id).orElse(null);
        // on recupère les données des attibuts du champ puis on le modifie
        assert incident1 != null;  // <-- On verifie que le id n'est pas null -->
        incident1.setIdIncident(incident.getIdIncident());
        incident1.setDocument(incident.getDocument());
        incident1.setLibelle(incident.getLibelle());
        incident1.setDescription(incident.getDescription());
        incident1.setDateJour(incident.getDateJour());
        incident1.setResume(incident.getResume());
        // On sauvegarde la modification
        return incidentRepository.save(incident1);
    }

    @Override
    public Incident getById(Long id) {
        // On recupère le id courant de la donnée
        return incidentRepository.findById(id).orElse(null);
    }

    @Override
    public int getCountAll() {
        return 0;
    }
}
