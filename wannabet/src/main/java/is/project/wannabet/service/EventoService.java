package is.project.wannabet.service;

import is.project.wannabet.factory.EventoFactory;
import is.project.wannabet.model.Quota;
import is.project.wannabet.repository.QuotaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import is.project.wannabet.model.Evento;
import is.project.wannabet.repository.EventoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Servizio per la gestione degli eventi nel sistema di scommesse.
 * Fornisce metodi per creare, cercare, aggiornare e chiudere eventi,
 * oltre a gestire le quote associate.
 */
@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private QuotaRepository quotaRepository;

    /**
     * Recupera tutti gli eventi disponibili nel sistema.
     *
     * @return Lista di eventi esistenti.
     */
    public List<Evento> getAllEventi() {
        return eventoRepository.findAll();
    }

    /**
     * Recupera un evento tramite il suo ID.
     *
     * @param id ID dell'evento da recuperare.
     * @return Optional contenente l'evento se trovato, altrimenti vuoto.
     */
    public Optional<Evento> getEventoById(Long id) {
        return eventoRepository.findById(id);
    }

    @Transactional
    public Evento updateEvento(Long idEvento, Evento eventoDetails) {
        Optional<Evento> eventoOpt = eventoRepository.findById(idEvento);
        if (eventoOpt.isPresent()) {
            Evento evento = eventoOpt.get();
            evento.setNome(eventoDetails.getNome());
            evento.setDescrizione(eventoDetails.getDescrizione());
            evento.setCategoria(eventoDetails.getCategoria());
            evento.setChiuso(eventoDetails.isChiuso());
            return eventoRepository.save(evento);
        }
        throw new EntityNotFoundException("Evento non trovato");
    }

    /**
     * Trova eventi per categoria.
     */
    @Transactional(readOnly = true)
    public List<Evento> findEventoByCategoria(String categoria) {
        return eventoRepository.findEventoByCategoria(categoria);
    }

    /**
     * Trova eventi per categoria ordinati per data (dal più vecchio al più recente).
     */
    @Transactional(readOnly = true)
    public List<Evento> findEventoByCategoriaOrderByDataAsc(String categoria) {
        return eventoRepository.findEventoByCategoriaOrderByDataAsc(categoria);
    }

    /**
     * Trova eventi per categoria e descrizione.
     */
    @Transactional(readOnly = true)
    public List<Evento> findEventoByCategoriaAndDescrizione(String categoria, String descrizione) {
        return eventoRepository.findEventoByCategoriaAndDescrizione(categoria, descrizione);
    }

    @Transactional
    public void flush() {
        eventoRepository.flush(); // Forza Hibernate a salvare subito
    }


    /**
     * Cerca eventi basandosi sul nome, ignorando le maiuscole/minuscole.
     *
     * @param nome Nome (o parte del nome) dell'evento da cercare.
     * @return Lista di eventi che contengono il nome specificato.
     */
    public List<Evento> searchEventiByNome(String nome) {
        return eventoRepository.findByNomeContainingIgnoreCase(nome);
    }

    /**
     * Salva un nuovo evento o aggiorna un evento esistente.
     *
     * @param evento Oggetto Evento da salvare.
     * @return Evento salvato o aggiornato.
     */
    public Evento saveEvento(Evento evento) {
        return eventoRepository.save(evento);
    }

    /**
     * Elimina un evento dal database in base al suo ID.
     *
     * @param id ID dell'evento da eliminare.
     */
    public void deleteEvento(Long id) {
        eventoRepository.deleteById(id);
    }

    /**
     * Chiude un evento specifico e aggiorna tutte le quote associate,
     * impostandole come chiuse.
     *
     * @param idEvento ID dell'evento da chiudere.
     */
    public void chiudiEvento(Long idEvento) {
        Optional<Evento> eventoOpt = eventoRepository.findById(idEvento);
        if (eventoOpt.isPresent()) {
            Evento evento = eventoOpt.get();
            evento.setChiuso(true);
            eventoRepository.save(evento);

            // Aggiorna tutte le quote associate all'evento come chiuse
            List<Quota> quoteEvento = quotaRepository.findByEvento_IdEvento(idEvento);
            for (Quota quota : quoteEvento) {
                quota.setChiusa(true);
                quotaRepository.save(quota);
            }
        }
    }

    /**
     * Crea un nuovo evento utilizzando la factory e lo salva nel database.
     *
     * @param nome        Nome dell'evento.
     * @param data        Data dell'evento.
     * @param descrizione Descrizione dell'evento.
     * @param categoria   Categoria dell'evento (es. calcio, basket, etc.).
     * @return Evento creato e salvato nel database.
     */
    public Evento createEvento(String nome, Date data, String descrizione, String categoria) {
        Evento evento = EventoFactory.createEvento(nome, data, descrizione, categoria);
        return eventoRepository.save(evento);
    }
}
