package is.project.wannabet.service;

import is.project.wannabet.model.QuotaGiocata;
import is.project.wannabet.repository.QuotaGiocataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servizio per la gestione delle quote giocate all'interno delle scommesse.
 * Fornisce metodi per recuperare e salvare le quote associate alle scommesse.
 */
@Service
public class QuotaGiocataService {

    @Autowired
    private QuotaGiocataRepository quotaGiocataRepository;

    /**
     * Recupera tutte le quote giocate associate a una specifica scommessa.
     *
     * @param idScommessa ID della scommessa.
     * @return Lista di quote giocate per la scommessa specificata.
     */
    public List<QuotaGiocata> getQuoteGiocateByScommessa(Long idScommessa) {
        return quotaGiocataRepository.findByScommessa_IdScommessa(idScommessa);
    }

    @Transactional
    public void flush() {
        quotaGiocataRepository.flush();
    }

    /**
     * Salva una nuova quota giocata nel database.
     *
     * @param quotaGiocata Oggetto QuotaGiocata da salvare.
     */
    public void salvaQuotaGiocata(QuotaGiocata quotaGiocata) {
        quotaGiocataRepository.save(quotaGiocata);
    }
}
