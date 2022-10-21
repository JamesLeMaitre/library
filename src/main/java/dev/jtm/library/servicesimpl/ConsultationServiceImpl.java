package dev.jtm.library.servicesimpl;

import dev.jtm.library.entities.Consultation;
import dev.jtm.library.entities.Document;
import dev.jtm.library.repositories.ConsultationRepository;
import dev.jtm.library.repositories.DocumentRepository;
import dev.jtm.library.services.ConsultationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {
    private final ConsultationRepository consultationRepository;
    private final DocumentRepository documentRepository;

    @Override
    public List<Consultation> getAll() {
        return consultationRepository.findAll();
    }

    @Override
    public Consultation create(Consultation consultation, Long idDocument) {
        Document doc = documentRepository.findById(idDocument).orElse(null);
        consultation.setDocument(doc);
        return consultationRepository.save(consultation);
    }

    @Override
    public void delete(Long id) {
        consultationRepository.deleteById(id);
    }

    @Override
    public Consultation edit(Consultation consultation, Long id) {
        Consultation cons = getById(id);
        cons.setIdConsultation(consultation.getIdConsultation());
        cons.setDescription(consultation.getDescription());
        cons.setLibelle(consultation.getLibelle());
        cons.setDocument(consultation.getDocument());
        cons.setMotif(consultation.getMotif());
        cons.setDateConsul(consultation.getDateConsul());
        cons.setObservation(consultation.getObservation());
        return consultationRepository.save(cons);
    }


    @Override
    public Consultation getById(Long id) {
        return consultationRepository.findById(id).orElse(null);
    }

    @Override
    public int getCountAll() {
        return 0;
    }
}
