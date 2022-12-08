package dev.jtm.library.servicesimpl;

import dev.jtm.library.entities.Documents;
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
    public List<Documents> getAll() {
        return documentRepository.findAll();
    }

    @Override
    public Documents create(Documents document, Long idNature, Long idRayons) {
        Nature nature = natureRepository.findById(idNature).orElse(null);
        Rayons rayons = rayonsRepository.findById(idRayons).orElse(null);
        document.setNature(nature);
        document.setRayons(rayons);
        document.setQte_res(document.getQuantite());
        return documentRepository.save(document);
    }

    @Override
    public void delete(Long id) {
        documentRepository.deleteById(id);
    }

    @Override
    public Documents edit(Documents document, Long id) {
        Documents document1 = documentRepository.findById(id).orElse(null);
        assert document1 != null;
        document1.setId(document.getId());
        document1.setRayons(document.getRayons());
        document1.setAnnee_edition(document.getAnnee_edition());
        document1.setNature(document.getNature());
        document1.setDescription(document.getDescription());
        document1.setIsbn(document.getIsbn());
        document1.setNom(document.getNom());
        document1.setPrenom(document.getPrenom());
        document1.setLibelle(document.getLibelle());
        document1.setNbPages(document.getNbPages());
        document1.setQuantite(document.getQuantite());
        document1.setQuantite(document.getQuantite());
        document1.setTheme_titre(document.getTheme_titre());
        document1.setRefer(document.getRefer());
        document1.setState(document.isState());
        return documentRepository.save(document1);
    }

    @Override
    public Documents getById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }

    @Override
    public int getCountAll() {
        return 0;
    }
}
