package is.project.wannabet.repository;

import is.project.wannabet.model.AccountRegistrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository per l'entità AccountRegistrato.
 * Fornisce accesso ai dati degli account registrati.
 */
@Repository
public interface AccountRegistratoRepository extends JpaRepository<AccountRegistrato, Long> {

    /**
     * Trova un account registrato tramite il codice fiscale.
     *
     * @param codiceFiscale Codice fiscale dell'utente.
     * @return Optional contenente l'account registrato, se presente.
     */
    Optional<AccountRegistrato> findByCodiceFiscale(String codiceFiscale);
}
