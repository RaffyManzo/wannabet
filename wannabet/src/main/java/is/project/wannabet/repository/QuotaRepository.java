package is.project.wannabet.repository;

import is.project.wannabet.model.StatoQuota;
import org.springframework.data.jpa.repository.JpaRepository;
import is.project.wannabet.model.Quota;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface QuotaRepository extends JpaRepository<Quota, Long> {
    /**
     * Trova tutte le quote associate a un determinato evento.
     */
    @Query("SELECT q FROM Quota q WHERE q.evento.idEvento = :eventoId")
    List<Quota> findByEventoId(@Param("eventoId") Long eventoId);
}
