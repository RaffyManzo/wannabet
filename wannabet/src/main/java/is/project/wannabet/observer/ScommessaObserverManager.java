package is.project.wannabet.observer;

import is.project.wannabet.observer.QuotaCache;
import is.project.wannabet.model.*;
import is.project.wannabet.service.ContoService;
import is.project.wannabet.service.ScommessaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Classe che gestisce l'aggiornamento dello stato delle scommesse
 * quando cambia lo stato di una quota osservata.
 */
@Service
public class ScommessaObserverManager implements QuotaObserver {

    private final ScommessaService scommessaService;
    private final ContoService contoService;
    private final QuotaCache quotaCache;

    @Autowired
    public ScommessaObserverManager(ScommessaService scommessaService, ContoService contoService, QuotaCache quotaCache) {
        this.scommessaService = scommessaService;
        this.contoService = contoService;
        this.quotaCache = quotaCache;
    }

    /**
     * Metodo chiamato automaticamente quando cambia lo stato di una quota.
     */
    @Override
    public void update(Long idQuota) {
        Quota quota = quotaCache.getQuota(idQuota);
        if (quota == null) return;

        List<Scommessa> scommesse = scommessaService.getScommesseByQuota(idQuota);
        for (Scommessa s : scommesse) {
            aggiornaStatoScommessa(s);
        }
    }

    /**
     * Aggiorna lo stato di una scommessa in base allo stato delle sue quote giocate.
     */
    private void aggiornaStatoScommessa(Scommessa scommessa) {
        if(scommessa.getTipo() == TipoScommessa.GIOCATA) {

            boolean almenoUnaPerdente = scommessa.getQuoteGiocate().stream()
                    .anyMatch(q -> q.getQuota().getStato() == StatoQuota.PERDENTE);

            boolean tutteVincenti = scommessa.getQuoteGiocate().stream()
                    .allMatch(q -> q.getQuota().getStato() == StatoQuota.VINCENTE);

            if (almenoUnaPerdente) {
                scommessa.setStato(StatoScommessa.PERSA);
            } else if (tutteVincenti) {
                scommessa.setStato(StatoScommessa.VINTA);
                // contoService.aggiornaSaldoDopoVincita(scommessa); // TODO: disabilitato pere testing
            }

            scommessaService.saveScommessa(scommessa);
        }
    }
}
