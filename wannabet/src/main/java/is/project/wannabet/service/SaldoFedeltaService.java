package is.project.wannabet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import is.project.wannabet.model.SaldoFedelta;
import is.project.wannabet.repository.SaldoFedeltaRepository;
import org.springframework.transaction.annotation.Transactional;

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

        @Transactional
        public SaldoFedelta saveSaldoFedelta(SaldoFedelta saldoFedelta) {
            saldoFedelta = saldoFedeltaRepository.save(saldoFedelta);
            saldoFedeltaRepository.flush();
            return saldoFedelta;
        }



    @Transactional
    public void flush() {
        saldoFedeltaRepository.flush();
    }

    public void deleteSaldoFedelta(Long id) {
        saldoFedeltaRepository.deleteById(id);
    }
}
