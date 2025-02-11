package is.project.wannabet.service;

import is.project.wannabet.factory.QuotaFactory;
import is.project.wannabet.observer.QuotaManager;
import is.project.wannabet.model.Evento;
import is.project.wannabet.model.Quota;
import is.project.wannabet.model.StatoQuota;
import is.project.wannabet.observer.QuotaObserver;
import is.project.wannabet.repository.QuotaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servizio per la gestione delle quote nel sistema di scommesse.
 */
@Service
public class QuotaService {

    @Autowired
    private is.project.wannabet.observer.QuotaCache quotaCache;

    @Autowired
    private QuotaRepository quotaRepository;


    @Autowired
    private QuotaManager quotaManager;

    /**
     * Restituisce tutte le quote presenti nel database.
     *
     * @return Lista di tutte le quote disponibili.
     */
    public List<Quota> getAllQuote() {
        return quotaRepository.findAll();
    }

    /**
     * Recupera una quota tramite il suo ID.
     *
     * @param id ID della quota da cercare.
     * @return Optional contenente la quota, se presente.
     */
    public Optional<Quota> getQuotaById(Long id) {
        return quotaRepository.findById(id);
    }

    /**
     * Restituisce tutte le quote associate a un determinato evento.
     *
     * @param eventoId ID dell'evento.
     * @return Lista delle quote associate all'evento specificato.
     */
    public List<Quota> getQuoteByEvento(Long eventoId) {
        return quotaRepository.findByEvento_IdEvento(eventoId);
    }

    /**
     * Salva una nuova quota nel database e la registra nel `QuotaManager`.
     *
     * @param quota Oggetto quota da salvare.
     * @return Quota salvata nel database.
     */
    public Quota saveQuota(Quota quota) {
        Quota savedQuota = quotaRepository.save(quota);
        quotaManager.aggiornaQuota(savedQuota); // Registra la quota nel sistema globale
        return savedQuota;
    }

    /**
     * Salva la mopdifica della quota nel database e la registra nel `QuotaManager`.
     *
     * @param quotaDetails Oggetto quota da aggiornare nel db.
     */
    @Transactional
    public Quota updateQuota(Long idQuota, Quota quotaDetails) {
        Optional<Quota> quotaOpt = quotaRepository.findById(idQuota);
        if (quotaOpt.isPresent()) {
            Quota quota = quotaOpt.get();
            quota.setMoltiplicatore(quotaDetails.getMoltiplicatore());
            quota.setEsito(quotaDetails.getEsito());
            quota.setCategoria(quotaDetails.getCategoria());
            quota.setChiusa(quotaDetails.isChiusa());
            quota.setStato(quotaDetails.getStato());
            quota = quotaRepository.save(quota);
            quotaManager.aggiornaQuota(quota); // Aggiorna cache e notifica observer
            return quota;
        }
        throw new EntityNotFoundException("Quota non trovata");
    }


    /**
     * Elimina una quota in base al suo ID.
     *
     * @param id ID della quota da eliminare.
     */
    public void deleteQuota(Long id) {
        quotaRepository.deleteById(id);
        quotaCache.rimuoviQuota(id); // Rimuove la quota dal sistema globale
    }

    /**
     * Registra un Observer per il monitoraggio delle quote.
     *
     * @param observer Observer da registrare.
     */
    public void registerObserver(QuotaObserver observer) {
        quotaManager.addObserver(observer);
    }

    /**
     * Referta una quota aggiornandone lo stato e notificando gli osservatori.
     *
     * @param idQuota ID della quota da refertare.
     * @param referto Referto assegnato alla quota.
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

    @Transactional
    public void flush() {
        quotaRepository.flush();
    }

    /**
     * Crea una nuova quota tramite la Factory e la registra nel sistema.
     *
     * @param esito         Esito della quota.
     * @param categoria     Categoria della quota.
     * @param moltiplicatore Moltiplicatore della quota.
     * @param evento        Evento associato alla quota.
     * @return Quota creata e salvata nel database.
     */
    public Quota createQuota(String esito, String categoria, double moltiplicatore, Evento evento) {
        Quota quota = QuotaFactory.createQuota(evento, moltiplicatore, esito, categoria);
        Quota savedQuota = quotaRepository.save(quota);
        quotaManager.aggiornaQuota(savedQuota); // Registra la quota nel `QuotaManager`
        return savedQuota;
    }

    /**
     * Crea una nuova quota con lo stato specificato.
     *
     * @param esito         Esito della quota.
     * @param categoria     Categoria della quota.
     * @param moltiplicatore Moltiplicatore della quota.
     * @param evento        Evento associato alla quota.
     * @param chiusa        Stato della quota (aperta/chiusa).
     * @return Quota creata e salvata nel database.
     */
    public Quota createQuota(String esito, String categoria, double moltiplicatore, Evento evento, boolean chiusa) {
        Quota quota = QuotaFactory.createQuota(evento, moltiplicatore, esito, categoria, chiusa);
        Quota savedQuota = quotaRepository.save(quota);
        quotaManager.aggiornaQuota(savedQuota); // Registra la quota nel `QuotaManager`
        return savedQuota;
    }
}
