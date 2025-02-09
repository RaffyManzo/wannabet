package is.project.wannabet.repository;

import is.project.wannabet.model.Scommessa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScommessaRepository extends JpaRepository<Scommessa, Long> {

    /**
     * Trova tutte le scommesse di un determinato utente usando `idAccount`.
     */
    List<Scommessa> findByAccount_IdAccount(Long accountId);

    /**
     * Trova tutte le scommesse che contengono una determinata quota giocata.
     */
    @Query("SELECT s FROM Scommessa s JOIN s.quoteGiocate qg WHERE qg.quota.idQuota = :idQuota")
    List<Scommessa> findScommesseByQuotaId(@Param("idQuota") Long idQuota);
}
