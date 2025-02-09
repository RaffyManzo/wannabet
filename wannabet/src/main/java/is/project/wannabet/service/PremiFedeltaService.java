package is.project.wannabet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import is.project.wannabet.model.PremiFedelta;
import is.project.wannabet.repository.PremiFedeltaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PremiFedeltaService {

    @Autowired
    private PremiFedeltaRepository premiFedeltaRepository;

    public List<PremiFedelta> getAllPremi() {
        return premiFedeltaRepository.findAll();
    }

    public Optional<PremiFedelta> getPremioById(Long id) {
        return premiFedeltaRepository.findById(id);
    }

    public PremiFedelta savePremio(PremiFedelta premio) {
        return premiFedeltaRepository.save(premio);
    }

    public void deletePremio(Long id) {
        premiFedeltaRepository.deleteById(id);
    }
}
