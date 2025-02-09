package is.project.wannabet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import is.project.wannabet.model.Scommessa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScommessaRepository extends JpaRepository<Scommessa, Long> {
    List<Scommessa> findByAccountId(Long accountId); // Trova tutte le scommesse di un utente

    @Query("SELECT s FROM Scommessa s JOIN s.quote q WHERE q.idQuota = :idQuota")
    List<Scommessa> findScommesseByQuotaId(@Param("idQuota") Long idQuota);

}

