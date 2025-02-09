package is.project.wannabet.service;

import is.project.wannabet.factory.QuotaFactory;
import is.project.wannabet.controller.QuotaManager;
import is.project.wannabet.model.Evento;
import is.project.wannabet.model.Quota;
import is.project.wannabet.model.StatoQuota;
import is.project.wannabet.observer.QuotaObserver;
import is.project.wannabet.repository.QuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuotaService {

    @Autowired
    private QuotaRepository quotaRepository;

    private final QuotaManager quotaManager = QuotaManager.getInstance();

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
        return quotaRepository.findByEventoId(eventoId);
    }

    /**
     * Salva una nuova quota nel database e la registra nel `QuotaManager`.
     */
    public Quota saveQuota(Quota quota) {
        Quota savedQuota = quotaRepository.save(quota);
        quotaManager.aggiornaQuota(savedQuota); // Registra la quota nel sistema globale
        return savedQuota;
    }

    /**
     * Elimina una quota in base al suo ID.
     */
    public void deleteQuota(Long id) {
        quotaRepository.deleteById(id);
        quotaManager.rimuoviQuota(id); // Rimuove la quota dal sistema globale
    }

    /**
     * Registra un Observer per il monitoraggio delle quote.
     */
    public void registerObserver(QuotaObserver observer) {
        quotaManager.addObserver(observer);
    }

    /**
     * Referta una quota aggiornandone lo stato e notificando gli osservatori.
     */
    public void refertaQuota(Long idQuota, String referto) {
        Optional<Quota> quotaOpt = quotaRepository.findById(idQuota);
        if (quotaOpt.isPresent()) {
            Quota quota = quotaOpt.get();
            if (referto.equals(quota.getEsito())) {
                quota.setStato(StatoQuota.VINCENTE);
            } else {
                quota.setStato(StatoQuota.PERDENTE);
            }
            quotaRepository.save(quota);

            // Notifica gli observer della modifica dello stato
            quotaManager.aggiornaQuota(quota);
        }
    }

    /**
     * Crea una nuova quota tramite la Factory e la registra nel sistema.
     */
    public Quota createQuota(String esito, String categoria, double moltiplicatore, Evento evento) {
        Quota quota = QuotaFactory.createQuota(evento, moltiplicatore, esito, categoria);
        Quota savedQuota = quotaRepository.save(quota);
        quotaManager.aggiornaQuota(savedQuota); // Registra la quota nel `QuotaManager`
        return savedQuota;
    }
}
