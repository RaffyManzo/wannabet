package is.project.wannabet.repository;

import is.project.wannabet.model.Scommessa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Repository per l'entità Scommessa.
 * Fornisce metodi per accedere e manipolare i dati delle scommesse nel database.
 */
@Repository
public interface ScommessaRepository extends JpaRepository<Scommessa, Long> {

    /**
     * Trova tutte le scommesse associate a un determinato account.
     *
     * @param idAccount ID dell'account utente.
     * @return Lista di scommesse associate all'account specificato.
     */
    List<Scommessa> findByAccount_IdAccount(Long idAccount);

    /**
     * Trova una scommessa specifica verificando che appartenga all'account richiesto.
     *
     * @param idScommessa ID della scommessa.
     * @return La scommessa se trovata, altrimenti un Optional vuoto.
     */
    Optional<Scommessa> findByIdScommessa(Long idScommessa);

    /**
     * Trova tutte le scommesse che contengono una determinata quota.
     *
     * @param idQuota ID della quota.
     * @return Lista di scommesse che includono la quota specificata.
     */
    @Query("SELECT s FROM Scommessa s JOIN s.quoteGiocate qg WHERE qg.quota.idQuota = :idQuota")
    List<Scommessa> findScommesseByQuotaId(@Param("idQuota") Long idQuota);

}

