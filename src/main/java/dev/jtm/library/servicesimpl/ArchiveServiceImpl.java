package dev.jtm.library.servicesimpl;

import dev.jtm.library.entities.Archives;
import dev.jtm.library.entities.Rayons;
import dev.jtm.library.repositories.ArchiveRepository;
import dev.jtm.library.repositories.RayonsRepository;
import dev.jtm.library.services.ArchivesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ArchiveServiceImpl implements ArchivesService {
    private final ArchiveRepository archiveRepository;
    private final RayonsRepository rayonsRepository;


    @Override
    public List<Archives> getAll() {
        return archiveRepository.findAll();
    }

    @Override
    public Archives create(Archives archives, Long idRayons) {
        Rayons rayons = rayonsRepository.findById(idRayons).orElse(null);
        archives.setRayons(rayons);
        return archiveRepository.save(archives);
    }

    @Override
    public void delete(Long id) {
    archiveRepository.deleteById(id);
    }

    @Override
    public Archives edit(Archives archives, Long id) {
        Archives archive = getById(id);
        archive.setId(archives.getId());
        archive.setDescription(archives.getDescription());
        archive.setLibelle(archives.getLibelle());
        archive.setRayons(archives.getRayons());
        return archiveRepository.save(archive);
    }

    @Override
    public Archives getById(Long id) {
        return archiveRepository.findById(id).orElse(null);
    }

    @Override
    public int getCountAll() {
        return 0;
    }
}
