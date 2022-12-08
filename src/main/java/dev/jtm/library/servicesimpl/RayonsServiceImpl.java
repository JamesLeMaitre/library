package dev.jtm.library.servicesimpl;

import dev.jtm.library.entities.Rayons;
import dev.jtm.library.repositories.RayonsRepository;
import dev.jtm.library.services.RayonsService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class RayonsServiceImpl implements RayonsService {
    private final RayonsRepository rayonsRepository;

    @Override
    public List<Rayons> getAll() {
        return rayonsRepository.findAll();
    }

    @Override
    public Rayons create(Rayons rayons) {
        return rayonsRepository.save(rayons);
    }

    @Override
    public void delete(Long id) {
        rayonsRepository.deleteById(id);
    }

    @Override
    public Rayons edit(Rayons rayons, Long id) {
        Rayons rayons1 = rayonsRepository.findById(id).orElse(null);

        assert rayons1 != null : "ID null";
        rayons1.setId(rayons.getId());
       rayons1.setAcronym(rayons.getAcronym());
       rayons1.setLibelle(rayons.getLibelle());
        return rayonsRepository.save(rayons1);
    }

    @Override
    public Rayons getById(Long id) {
        Rayons rayons = rayonsRepository.findById(id).orElse(null);
        return rayons;
    }

    @Override
    public int getCountAll() {
        return rayonsRepository.countRayonsByIdRayons();
    }

    @Override
    public List<Rayons> limitRayons() {
        return rayonsRepository.limitRayons();
    }
}
