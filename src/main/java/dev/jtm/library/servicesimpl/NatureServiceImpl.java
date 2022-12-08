package dev.jtm.library.servicesimpl;

import dev.jtm.library.entities.Nature;
import dev.jtm.library.repositories.NatureRepository;
import dev.jtm.library.services.NatureService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class NatureServiceImpl implements NatureService {
    private final NatureRepository natureRepository;
    @Override
    public List<Nature> getAll() {
        return natureRepository.findAll();
    }

    @Override
    public Nature create(Nature nature) {
        return natureRepository.save(nature);
    }

    @Override
    public void delete(Long id) {
        natureRepository.deleteById(id);
    }

    @Override
    public Nature edit(Nature nature, Long id) {
        Nature na = natureRepository.findById(id).orElse(null);
        assert na != null;
        na.setId(nature.getId());
        na.setLibelle(nature.getLibelle());
        na.setDescription(nature.getDescription());
        return natureRepository.save(na);
    }

    @Override
    public Nature getById(Long id) {
        return natureRepository.findById(id).orElse(null);
    }

    @Override
    public int getCountAll() {
        return 0;
    }
}
