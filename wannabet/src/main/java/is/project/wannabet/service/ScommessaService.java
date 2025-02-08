package is.project.wannabet.service;

import is.project.wannabet.model.Quota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import is.project.wannabet.model.Scommessa;
import is.project.wannabet.repository.ScommessaRepository;
import is.project.wannabet.repository.QuotaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ScommessaService {

    @Autowired
    private ScommessaRepository scommessaRepository;

    @Autowired
    private QuotaRepository quotaRepository;

    public List<Scommessa> getAllScommesse() {
        return scommessaRepository.findAll();
    }

    public Optional<Scommessa> getScommessaById(Long id) {
        return scommessaRepository.findById(id);
    }

    public Scommessa saveScommessa(Scommessa scommessa) {
        // Assicuriamoci che tutte le quote esistano prima di salvare
        scommessa.setQuote(quotaRepository.findAllById(
                scommessa.getQuote().stream().map(Quota::getIdQuota).toList()
        ));
        return scommessaRepository.save(scommessa);
    }

    public void deleteScommessa(Long id) {
        scommessaRepository.deleteById(id);
    }
}
