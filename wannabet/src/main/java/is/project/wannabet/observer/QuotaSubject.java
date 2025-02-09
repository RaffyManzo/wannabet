package is.project.wannabet.observer;

public interface QuotaSubject {
    void addObserver(QuotaObserver observer);
    void removeObserver(QuotaObserver observer);
    void notifyObservers(Long idQuota);
}
