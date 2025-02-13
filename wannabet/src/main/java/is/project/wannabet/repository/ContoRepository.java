package is.project.wannabet.repository;

import is.project.wannabet.model.Conto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository per l'entità Conto.
 * Fornisce accesso ai dati relativi ai conti utente.
 */
@Repository
public interface ContoRepository extends JpaRepository<Conto, Long> {

    @Query("SELECT c FROM Conto c JOIN AccountRegistrato a ON c.idConto = a.conto.idConto WHERE a.idAccount = :idAccount")
    Conto findContoByAccountId(@Param("idAccount") Long idAccount);


}
