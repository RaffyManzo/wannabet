package is.project.wannabet.observer;

import is.project.wannabet.cache.QuotaCache;
import is.project.wannabet.model.Quota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Gestisce la registrazione e la notifica degli observer delle quote.
 */
@Component
public class QuotaManager {

    private final List<QuotaObserver> observers = new CopyOnWriteArrayList<>();
    private final QuotaCache quotaCache;

    @Autowired
    public QuotaManager(QuotaCache quotaCache) {
        this.quotaCache = quotaCache;
    }

    /**
     * Registra un nuovo observer per monitorare le modifiche alle quote.
     */
    public void addObserver(QuotaObserver observer) {
        observers.add(observer);
    }

    /**
     * Rimuove un observer registrato.
     */
    public void removeObserver(QuotaObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifica tutti gli observer riguardo a una modifica su una quota.
     */
    public void notifyObservers(Long idQuota) {
        for (QuotaObserver observer : observers) {
            observer.update(idQuota);
        }
    }

    /**
     * Registra un aggiornamento di una quota e notifica gli observer.
     */
    public void aggiornaQuota(Quota quota) {
        quotaCache.aggiornaQuota(quota); // Aggiorna la cache
        notifyObservers(quota.getIdQuota()); // Notifica gli observer
    }
}
