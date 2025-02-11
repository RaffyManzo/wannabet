package is.project.wannabet.cache;

import is.project.wannabet.model.Quota;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Classe che gestisce la cache delle quote per ridurre l'accesso al database.
 */
@Component
public class QuotaCache {

    private final Map<Long, Quota> quoteDisponibili = new ConcurrentHashMap<>();

    /**
     * Recupera una quota dalla cache.
     *
     * @param idQuota ID della quota.
     * @return La quota se presente, altrimenti null.
     */
    public Quota getQuota(Long idQuota) {
        return quoteDisponibili.get(idQuota);
    }

    /**
     * Aggiunge o aggiorna una quota nella cache.
     *
     * @param quota Quota da memorizzare.
     */
    public void aggiornaQuota(Quota quota) {
        quoteDisponibili.put(quota.getIdQuota(), quota);
    }

    /**
     * Rimuove una quota dalla cache.
     *
     * @param idQuota ID della quota da rimuovere.
     */
    public void rimuoviQuota(Long idQuota) {
        quoteDisponibili.remove(idQuota);
    }
}
