package is.project.wannabet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import is.project.wannabet.model.StoricoRiscossioni;
import is.project.wannabet.repository.StoricoRiscossioniRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StoricoRiscossioniService {

    @Autowired
    private StoricoRiscossioniRepository storicoRiscossioniRepository;

    public List<StoricoRiscossioni> getAllRiscossioni() {
        return storicoRiscossioniRepository.findAll();
    }

    public Optional<StoricoRiscossioni> getRiscossioneById(Long id) {
        return storicoRiscossioniRepository.findById(id);
    }

    public List<StoricoRiscossioni> getRiscossioniBySaldoFedelta(Long saldoFedeltaId) {
        return storicoRiscossioniRepository.findBySaldoFedelta_IdSaldoFedelta(saldoFedeltaId);
    }

    public StoricoRiscossioni saveRiscossione(StoricoRiscossioni riscossione) {
        return storicoRiscossioniRepository.save(riscossione);
    }

    public void deleteRiscossione(Long id) {
        storicoRiscossioniRepository.deleteById(id);
    }
}
