package is.project.wannabet.observer;

import is.project.wannabet.model.Quota;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class QuotaCache {

    private final Map<Long, Quota> quoteDisponibili = new ConcurrentHashMap<>();

    public Quota getQuota(Long idQuota) {
        return quoteDisponibili.get(idQuota);
    }

    public void aggiornaQuota(Quota quota) {
        quoteDisponibili.put(quota.getIdQuota(), quota);
    }

    public void rimuoviQuota(Long idQuota) {
        quoteDisponibili.remove(idQuota);
    }
}
