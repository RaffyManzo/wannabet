package is.project.wannabet.service;

import is.project.wannabet.model.Conto;
import is.project.wannabet.model.Scommessa;
import is.project.wannabet.repository.ContoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ContoService {

    @Autowired
    private ContoRepository contoRepository;

    public List<Conto> getAllConti() {
        return contoRepository.findAll();
    }

    public Optional<Conto> getContoById(Long id) {
        return contoRepository.findById(id);
    }

    public Conto saveConto(Conto conto) {
        return contoRepository.save(conto);
    }

    public void deleteConto(Long id) {
        contoRepository.deleteById(id);
    }


    @Transactional
    public void aggiornaSaldoDopoVincita(Scommessa scommessa) {
        if (scommessa.getStato() == is.project.wannabet.model.StatoScommessa.VINTA) {
            Optional<Conto> contoOpt = contoRepository.findByAccount_IdAccount(scommessa.getAccount().getIdAccount());

            if (contoOpt.isPresent()) {
                Conto conto = contoOpt.get();
                conto.aggiungiSaldo(scommessa.getVincita()); // Usa il campo `vincita` già presente
                contoRepository.save(conto);
            } else {
                throw new IllegalArgumentException("Conto non trovato per l'account");
            }
        }
    }
}
