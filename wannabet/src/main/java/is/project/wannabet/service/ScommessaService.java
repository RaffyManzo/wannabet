package is.project.wannabet.service;

import is.project.wannabet.factory.ScommessaFactory;
import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.model.Quota;
import is.project.wannabet.model.QuotaGiocata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import is.project.wannabet.model.Scommessa;
import is.project.wannabet.repository.ScommessaRepository;
import is.project.wannabet.repository.QuotaRepository;
import org.springframework.transaction.annotation.Transactional;

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
        // Verifichiamo che tutte le quote esistano
        List<QuotaGiocata> quoteGiocate = scommessa.getQuoteGiocate().stream()
                .map(qg -> new QuotaGiocata(scommessa, quotaRepository.findById(qg.getQuota().getIdQuota())
                        .orElseThrow(() -> new IllegalArgumentException("Quota non trovata con ID: " + qg.getQuota().getIdQuota()))))
                .toList();

        // Assegniamo le quote giocate alla scommessa
        scommessa.setQuoteGiocate(quoteGiocate);

        // Salviamo la scommessa con le quote giocate
        return scommessaRepository.save(scommessa);
    }


    public void deleteScommessa(Long id) {
        scommessaRepository.deleteById(id);
    }

    public Scommessa createScommessa(AccountRegistrato account, List<Quota> quote, double importo) {
        // Usa la Factory per creare una Scommessa
        Scommessa scommessa = ScommessaFactory.createScommessa(account, quote, importo);

        // Salva la scommessa nel database
        return scommessaRepository.save(scommessa);
    }

    @Transactional
    public void creaScommessaDaScontrino(List<Quota> quote, double importo) {
        if (quote == null || quote.isEmpty()) {
            throw new IllegalArgumentException("Una scommessa deve contenere almeno una quota.");
        }
        if (importo <= 0) {
            throw new IllegalArgumentException("L'importo della scommessa deve essere positivo.");
        }

        // Supponiamo che l'account venga identificato in sessione (da implementare con Spring Security)
        AccountRegistrato account = getAccountCorrente(); // TODO: Recuperare l'account dell'utente loggato

        Scommessa scommessa = ScommessaFactory.createScommessa(account, quote, importo);
        scommessaRepository.save(scommessa);
    }

    private AccountRegistrato getAccountCorrente() {
        // TODO: Recuperare l'account dalla sessione o dal contesto di Spring Security
        return new AccountRegistrato(); // Placeholder temporaneo
    }
}
