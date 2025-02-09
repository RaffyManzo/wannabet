package is.project.wannabet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import is.project.wannabet.model.SaldoFedelta;
import is.project.wannabet.repository.SaldoFedeltaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SaldoFedeltaService {

    @Autowired
    private SaldoFedeltaRepository saldoFedeltaRepository;

    public List<SaldoFedelta> getAllSaldiFedelta() {
        return saldoFedeltaRepository.findAll();
    }

    public Optional<SaldoFedelta> getSaldoFedeltaById(Long id) {
        return saldoFedeltaRepository.findById(id);
    }

    public SaldoFedelta saveSaldoFedelta(SaldoFedelta saldoFedelta) {
        return saldoFedeltaRepository.save(saldoFedelta);
    }

    public void deleteSaldoFedelta(Long id) {
        saldoFedeltaRepository.deleteById(id);
    }
}
