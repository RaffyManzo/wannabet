package is.project.wannabet.observer;

import java.util.ArrayList;
import java.util.List;

public class QuotaNotifier implements QuotaSubject {

    private final List<QuotaObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(QuotaObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(QuotaObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Long idQuota) {
        for (QuotaObserver observer : observers) {
            observer.update(idQuota);
        }
    }
}
