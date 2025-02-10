package is.project.wannabet.repository;

import is.project.wannabet.model.QuotaGiocata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository per l'entità QuotaGiocata.
 * Gestisce l'accesso ai dati delle quote giocate.
 */
@Repository
public interface QuotaGiocataRepository extends JpaRepository<QuotaGiocata, Long> {
    List<QuotaGiocata> findByScommessa_IdScommessa(Long idScommessa);
}
