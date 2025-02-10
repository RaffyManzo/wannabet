package is.project.wannabet.controller;

import is.project.wannabet.model.Quota;
import is.project.wannabet.observer.QuotaObserver;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class QuotaManager {

    private static QuotaManager instance;
    private final Map<Long, Quota> quoteDisponibili;
    private final List<QuotaObserver> observers;

    private QuotaManager() {
        this.quoteDisponibili = new ConcurrentHashMap<>();
        this.observers = new ArrayList<>();
    }

    public static synchronized QuotaManager getInstance() {
        if (instance == null) {
            instance = new QuotaManager();
        }
        return instance;
    }

    public void aggiornaQuota(Quota quota) {
        quoteDisponibili.put(quota.getIdQuota(), quota);
        notifyObservers(quota.getIdQuota());
    }

    public Quota getQuota(Long idQuota) {
        return quoteDisponibili.get(idQuota);
    }

    public void rimuoviQuota(Long idQuota) {
        quoteDisponibili.remove(idQuota);
        notifyObservers(idQuota);
    }

    public void chiudiQuota(Long idQuota) {
        Quota quota = quoteDisponibili.get(idQuota);
        if (quota != null) {
            quota.setChiusa(true);
            aggiornaQuota(quota);
        }
    }

    public void reset() {
        quoteDisponibili.clear();
        observers.clear();
    }

    // Observer Methods
    public void addObserver(QuotaObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(QuotaObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(Long idQuota) {
        for (QuotaObserver observer : observers) {
            observer.update(idQuota);
        }
    }
}
