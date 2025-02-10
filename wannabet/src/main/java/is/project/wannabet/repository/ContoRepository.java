package is.project.wannabet.repository;

import is.project.wannabet.model.Conto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository per l'entità Conto.
 * Fornisce accesso ai dati relativi ai conti utente.
 */
@Repository
public interface ContoRepository extends JpaRepository<Conto, Long> {

}
