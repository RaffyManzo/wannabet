package is.project.wannabet.observer;

import is.project.wannabet.controller.QuotaManager;
import is.project.wannabet.model.*;
import is.project.wannabet.service.ContoService;
import is.project.wannabet.service.ScommessaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;

/**
 * Classe che gestisce l'aggiornamento dello stato delle scommesse
 * quando cambia lo stato di una quota osservata.
 * Implementa il pattern Observer per monitorare le variazioni delle quote.
 */
@Service
public class ScommessaObserverManager implements QuotaObserver {

    private final ScommessaService scommessaService;
    private final ContoService contoService;
    private final QuotaManager quotaManager;

    @Autowired
    public ScommessaObserverManager(ScommessaService scommessaService, ContoService contoService, QuotaManager quotaManager) {
        this.scommessaService = scommessaService;
        this.contoService = contoService;
        this.quotaManager = quotaManager;
        // this.quotaManager.addObserver(this); // Registriamo subito l'observer
    }


    /**
     * Metodo chiamato automaticamente quando cambia lo stato di una quota.
     * Recupera tutte le scommesse associate alla quota modificata e aggiorna il loro stato.
     *
     * @param idQuota ID della quota che ha subito una modifica di stato.
     */
    @Override
    public void update(Long idQuota) {
        List<Scommessa> scommesse = scommessaService.getScommesseByQuota(idQuota);
        for (Scommessa s : scommesse) {
            aggiornaStatoScommessa(s);
        }
    }

    /**
     * Aggiorna lo stato di una scommessa in base allo stato delle sue quote giocate.
     * Se almeno una quota è "PERDENTE", la scommessa viene considerata persa.
     * Se tutte le quote giocate sono "VINCENTI", la scommessa viene considerata vinta e
     * si dovrebbe aggiornare il saldo dell'utente.
     *
     * @param scommessa La scommessa da aggiornare.
     */
    private void aggiornaStatoScommessa(Scommessa scommessa) {
        boolean almenoUnaPerdente = scommessa.getQuoteGiocate().stream()
                .anyMatch(q -> q.getQuota().getStato() == StatoQuota.PERDENTE);

        boolean tutteVincenti = scommessa.getQuoteGiocate().stream()
                .allMatch(q -> q.getQuota().getStato() == StatoQuota.VINCENTE);

        if (almenoUnaPerdente) {
            scommessa.setStato(StatoScommessa.PERSA);
        } else if (tutteVincenti) {
            scommessa.setStato(StatoScommessa.VINTA);
            contoService.aggiornaSaldoDopoVincita(scommessa);
        }

        scommessaService.saveScommessa(scommessa);
    }
}
