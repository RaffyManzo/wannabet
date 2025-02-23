package is.project.wannabet.observer;

import is.project.wannabet.model.*;
import is.project.wannabet.service.ContoService;
import is.project.wannabet.service.ScommessaService;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ScommessaObserverManager implements QuotaObserver {

    private static final Logger logger = LoggerFactory.getLogger(ScommessaObserverManager.class);

    private final ScommessaService scommessaService;
    private final ContoService contoService;
    private final QuotaCache quotaCache;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public ScommessaObserverManager(ScommessaService scommessaService, ContoService contoService, QuotaCache quotaCache) {
        this.scommessaService = scommessaService;
        this.contoService = contoService;
        this.quotaCache = quotaCache;
    }

    @Override
    public void update(Long idQuota) {
        logger.debug("Ricevuta notifica di aggiornamento per quota id: {}", idQuota);
        // Per ogni scommessa che contiene la quota, ricarica l'entità da DB
        List<Scommessa> scommesse = scommessaService.getScommesseByQuota(idQuota);
        logger.debug("Trovate {} scommesse associate alla quota id: {}", scommesse.size(), idQuota);
        for (Scommessa s : scommesse) {
            Scommessa refreshed = scommessaService.getScommessaById(s.getIdScommessa()).orElse(s);
            logger.debug("Scommessa id: {} ricaricata per aggiornamento", refreshed.getIdScommessa());
            aggiornaStatoScommessa(refreshed);
        }
    }

    private void aggiornaStatoScommessa(Scommessa scommessa) {
        Scommessa refreshed = scommessaService.getScommessaById(scommessa.getIdScommessa()).orElse(scommessa);
        // Forza il caricamento della collezione lazy
        int size = refreshed.getQuoteGiocate().size();
        logger.debug("Numero di quote caricate per scommessa {}: {}", refreshed.getIdScommessa(), size);

        if (refreshed.getTipo() == TipoScommessa.GIOCATA) {
            try {
                entityManager.refresh(refreshed);
                logger.debug("EntityManager.refresh eseguito per scommessa id: {}", refreshed.getIdScommessa());
            } catch (Exception e) {
                logger.warn("Refresh fallito per scommessa id: {}. Continuo comunque.", refreshed.getIdScommessa());
            }

            refreshed.getQuoteGiocate().forEach(qg -> {
                logger.debug("Quota id: {} - Stato: {}", qg.getQuota().getIdQuota(), qg.getQuota().getStato());
            });

            boolean almenoUnaPerdente = refreshed.getQuoteGiocate().stream()
                    .anyMatch(q -> q.getQuota().getStato() == StatoQuota.PERDENTE);
            boolean tutteVincenti = refreshed.getQuoteGiocate().stream()
                    .allMatch(q -> q.getQuota().getStato() == StatoQuota.VINCENTE);

            if (almenoUnaPerdente) {
                logger.debug("Scommessa id: {} impostata a PERSA", refreshed.getIdScommessa());
                refreshed.setStato(StatoScommessa.PERSA);
            } else if (tutteVincenti) {
                logger.debug("Scommessa id: {} impostata a VINTA", refreshed.getIdScommessa());
                refreshed.setStato(StatoScommessa.VINTA);
                contoService.aggiornaSaldoDopoVincita(refreshed);
            } else {
                logger.debug("Scommessa id: {} non aggiornata (condizioni non soddisfatte)", refreshed.getIdScommessa());
            }
            scommessaService.saveScommessa(refreshed);
            logger.debug("Scommessa id: {} salvata con nuovo stato: {}", refreshed.getIdScommessa(), refreshed.getStato());
        }
    }

}
