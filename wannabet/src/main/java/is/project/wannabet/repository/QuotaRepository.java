package is.project.wannabet.repository;

import is.project.wannabet.model.Quota;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
