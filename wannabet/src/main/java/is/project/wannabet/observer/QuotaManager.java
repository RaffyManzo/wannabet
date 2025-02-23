package is.project.wannabet.observer;

import is.project.wannabet.model.Quota;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class QuotaManager {

    private static final Logger logger = LoggerFactory.getLogger(QuotaManager.class);

    private final List<QuotaObserver> observers = new CopyOnWriteArrayList<>();
    private final QuotaCache quotaCache;

    @Autowired
    public QuotaManager(QuotaCache quotaCache) {
        this.quotaCache = quotaCache;
    }

    public void addObserver(QuotaObserver observer) {
        observers.add(observer);
        logger.debug("Aggiunto observer: {}", observer.getClass().getSimpleName());
    }

    public void removeObserver(QuotaObserver observer) {
        observers.remove(observer);
        logger.debug("Rimosso observer: {}", observer.getClass().getSimpleName());
    }

    public void notifyObservers(Long idQuota) {
        logger.debug("Notifico gli observer per quota id: {}", idQuota);
        for (QuotaObserver observer : observers) {
            observer.update(idQuota);
        }
    }

    public void aggiornaQuota(Quota quota) {
        logger.debug("Aggiornamento quota id: {} nella cache", quota.getIdQuota());
        // Rimuovi il valore obsoleto e reinserisci quello aggiornato
        quotaCache.rimuoviQuota(quota.getIdQuota());
        quotaCache.aggiornaQuota(quota);
        logger.debug("Quota id: {} aggiornata nella cache", quota.getIdQuota());
        notifyObservers(quota.getIdQuota());
    }
}
