package is.project.wannabet.repository;

import is.project.wannabet.model.SaldoFedelta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository per l'entità SaldoFedelta.
 * Fornisce metodi per l'accesso ai punti fedeltà degli utenti.
 */
@Repository
public interface SaldoFedeltaRepository extends JpaRepository<SaldoFedelta, Long> {

}
