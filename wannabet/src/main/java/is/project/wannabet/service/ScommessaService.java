package is.project.wannabet.service;

import is.project.wannabet.factory.ScommessaFactory;
import is.project.wannabet.model.*;
import is.project.wannabet.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servizio per la gestione delle scommesse.
 * Le scommesse sono sempre associate a un account e includono una o più quote giocate.
 */
@Service
public class ScommessaService {

    @Autowired
    private ScommessaRepository scommessaRepository;

    @Autowired
    private QuotaService quotaService;

    @Autowired
    private AccountRegistratoRepository accountRegistratoRepository;

    /**
     * Recupera tutte le scommesse associate a un determinato account.
     *
     * @param idAccount ID dell'account dell'utente.
     * @return Lista delle scommesse dell'account specificato.
     */
    public List<Scommessa> getScommesseByAccount(Long idAccount) {
        return scommessaRepository.findByAccount_IdAccount(idAccount);
    }

    /**
     * Recupera una scommessa per ID solo se appartiene all'account specificato.
     *
     * @param idScommessa ID della scommessa.
     * @return Optional contenente la scommessa se trovata, altrimenti vuoto.
     */
    public Optional<Scommessa> getScommessaById(Long idScommessa) {
        return scommessaRepository.findByIdScommessa(idScommessa);
    }

    /**
     * Crea una nuova scommessa per un determinato account.
     *
     * @param scommessa Oggetto Scommessa da salvare.
     * @return La scommessa salvata.
     */
    public Scommessa saveScommessa(Scommessa scommessa) {
        Optional<AccountRegistrato> accountOpt = accountRegistratoRepository.findById(scommessa.getAccount().getIdAccount());

        if (accountOpt.isEmpty()) {
            throw new IllegalArgumentException("Account non trovato per ID: " + scommessa.getAccount().getIdAccount());
        }

        // Verifica che tutte le quote esistano prima di salvare la scommessa
        List<QuotaGiocata> quoteGiocate = scommessa.getQuoteGiocate().stream()
                .map(qg -> new QuotaGiocata(scommessa, quotaService.getQuotaById(qg.getQuota().getIdQuota())
                        .orElseThrow(() -> new IllegalArgumentException("Quota non trovata con ID: " + qg.getQuota().getIdQuota()))))
                .toList();

        scommessa.setQuoteGiocate(quoteGiocate);

        return scommessaRepository.save(scommessa);
    }

    /**
     * Recupera tutte le scommesse che contengono una determinata quota.
     *
     * @param idQuota ID della quota.
     * @return Lista di scommesse contenenti la quota specificata.
     */
    public List<Scommessa> getScommesseByQuota(Long idQuota) {
        return scommessaRepository.findScommesseByQuotaId(idQuota);
    }


    /**
     * Crea una scommessa a partire da un elenco di quote e un importo.
     *
     * @param quote    Lista delle quote giocate nella scommessa.
     * @param importo  Importo della scommessa.
     * @param idAccount ID dell'account che sta effettuando la scommessa.
     */
    @Transactional
    public void creaScommessaDaScontrino(List<Quota> quote, double importo, Long idAccount) {
        if (quote == null || quote.isEmpty()) {
            throw new IllegalArgumentException("Una scommessa deve contenere almeno una quota.");
        }
        if (importo <= 0) {
            throw new IllegalArgumentException("L'importo della scommessa deve essere positivo.");
        }

        Optional<AccountRegistrato> accountOpt = accountRegistratoRepository.findById(idAccount);
        if (accountOpt.isEmpty()) {
            throw new IllegalArgumentException("Account non trovato per ID: " + idAccount);
        }

        Scommessa scommessa = ScommessaFactory.createScommessa(accountOpt.get(), quote, importo);
        scommessaRepository.save(scommessa);
    }


}
