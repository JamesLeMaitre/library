package dev.jtm.library.servicesimpl;

import dev.jtm.library.entities.Document;
import dev.jtm.library.entities.Nature;
import dev.jtm.library.entities.Rayons;
import dev.jtm.library.repositories.DocumentRepository;
import dev.jtm.library.repositories.NatureRepository;
import dev.jtm.library.repositories.RayonsRepository;
import dev.jtm.library.services.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final NatureRepository natureRepository;
    private final RayonsRepository rayonsRepository;
    private final DocumentRepository documentRepository;

    @Override
    public List<Document> getAll() {
        return documentRepository.findAll();
    }

    @Override
    public Document create(Document document, Long idNature, Long idRayons) {
        Nature nature = natureRepository.findById(idNature).orElse(null);
        Rayons rayons = rayonsRepository.findById(idRayons).orElse(null);
        document.setNature(nature);
        document.setRayons(rayons);
        return documentRepository.save(document);
    }

    @Override
    public void delete(Long id) {
        documentRepository.deleteById(id);
    }

    @Override
    public Document edit(Document document, Long id) {
        Document document1 = documentRepository.findById(id).orElse(null);
        document1.setIdDocument(document.getIdDocument());
        return null;
    }

    @Override
    public Document getById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }

    @Override
    public int getCountAll() {
        return 0;
    }
}
