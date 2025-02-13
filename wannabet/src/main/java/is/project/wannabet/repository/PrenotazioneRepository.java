package is.project.wannabet.repository;

import is.project.wannabet.model.Prenotazione;
import is.project.wannabet.model.Quota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, String> {

    @Query("SELECT q FROM QuotaGiocata qg JOIN qg.quota q WHERE qg.scommessa.idScommessa = " +
            "(SELECT p.scommessa.idScommessa FROM Prenotazione p WHERE p.codice = :codice)")
    List<Quota> findQuoteByCodicePrenotazione(String codice);

    /**
     * Controlla se una prenotazione con il codice esiste già nel database.
     *
     * @param codice Codice della prenotazione.
     * @return true se il codice esiste già, false altrimenti.
     */
    boolean existsByCodice(String codice);

    Optional<Prenotazione> findByCodice(String codice);
}
