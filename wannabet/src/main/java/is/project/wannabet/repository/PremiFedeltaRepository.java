package is.project.wannabet.repository;

import is.project.wannabet.model.PremiFedelta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository per l'entità PremiFedelta.
 * Fornisce accesso ai dati relativi ai premi fedeltà riscattabili dagli utenti.
 */
@Repository
public interface PremiFedeltaRepository extends JpaRepository<PremiFedelta, Long> {

}
