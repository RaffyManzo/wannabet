package is.project.wannabet.service;

import is.project.wannabet.factory.QuotaFactory;
import is.project.wannabet.model.Evento;
import is.project.wannabet.model.Quota;
import is.project.wannabet.model.StatoQuota;
import is.project.wannabet.observer.QuotaNotifier;
import is.project.wannabet.observer.QuotaObserver;
import is.project.wannabet.observer.ScommessaObserverManager;
import is.project.wannabet.repository.QuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuotaService {

    @Autowired
    private QuotaRepository quotaRepository;

    @Autowired
    private ScommessaObserverManager scommessaObserverManager;

    private final QuotaNotifier quotaNotifier = new QuotaNotifier();

    /**
     * Restituisce tutte le quote presenti nel database.
     */
    public List<Quota> getAllQuote() {
        return quotaRepository.findAll();
    }

    /**
     * Recupera una quota tramite il suo ID.
     */
    public Optional<Quota> getQuotaById(Long id) {
        return quotaRepository.findById(id);
    }

    /**
     * Restituisce tutte le quote associate a un determinato evento.
     */
    public List<Quota> getQuoteByEvento(Long eventoId) {
        return quotaRepository.findByEvento_IdEvento(eventoId);
    }

    /**
     * Salva una nuova quota nel database.
     */
    public Quota saveQuota(Quota quota) {
        return quotaRepository.save(quota);
    }

    /**
     * Elimina una quota in base al suo ID.
     */
    public void deleteQuota(Long id) {
        quotaRepository.deleteById(id);
    }

    public void registerObserver(QuotaObserver observer) {
        quotaNotifier.addObserver(observer);
    }

    public void refertaQuota(Long idQuota, String referto) {
        Optional<Quota> quotaOpt = quotaRepository.findById(idQuota);
        if (quotaOpt.isPresent()) {
            Quota quota = quotaOpt.get();
            if (referto.equals(quota.getDescrizione())) {
                quota.setStato(StatoQuota.VINCENTE);
            } else {
                quota.setStato(StatoQuota.PERDENTE);
            }
            quota.setReferto(referto);
            quotaRepository.save(quota);

            // Notifica gli osservatori (ScommessaObserverManager)
            quotaNotifier.notifyObservers(idQuota);
        }
    }

    public Quota createQuota(String descrizione, String categoria, double moltiplicatore, Evento evento) {
        // Usa la Factory per creare l'oggetto Quota
        Quota quota = QuotaFactory.createQuota(descrizione, categoria, moltiplicatore, evento);

        // Salva la quota nel database
        return quotaRepository.save(quota);
    }
}
