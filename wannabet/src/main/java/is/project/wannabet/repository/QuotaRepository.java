package is.project.wannabet.repository;

import is.project.wannabet.model.Evento;
import is.project.wannabet.model.Quota;
import is.project.wannabet.model.StatoQuota;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository per l'entità Quota.
 * Gestisce l'accesso ai dati delle quote nel database.
 */
@Repository
public interface QuotaRepository extends JpaRepository<Quota, Long> {

    /**
     * Trova tutte le quote associate a un determinato evento.
     *
     * @param eventoId ID dell'evento.
     * @return Lista di quote associate all'evento specificato.
     */
    List<Quota> findByEvento_IdEvento(Long eventoId);

    @Query("SELECT q FROM Quota q JOIN q.evento e WHERE e.descrizione LIKE %:descrizione%")
    List<Quota> findAllByEventoDescrizione(@Param("descrizione") String descrizione);

    // Seleziona i 6 eventi prossimi per una categoria specifica, ordinati per data
    @Query("""
        SELECT e
        FROM Evento e
        WHERE e.data > CURRENT_TIMESTAMP
          AND e.categoria = :categoria
        ORDER BY e.data ASC
    """)
    Page<Evento> findTop6ByCategoria(@Param("categoria") String categoria, Pageable pageable);

    @Modifying
    @Query("UPDATE Quota q SET q.moltiplicatore = :moltiplicatore, q.stato = :stato, q.esito = :esito, q.categoria = :categoria, q.chiusa = :chiusa WHERE q.idQuota = :idQuota")
    void updateQuota(@Param("idQuota") Long idQuota, @Param("moltiplicatore") double moltiplicatore,
                     @Param("esito") String esito, @Param("categoria") String categoria, @Param("stato") StatoQuota stato, @Param("chiusa") boolean chiusa);

}
