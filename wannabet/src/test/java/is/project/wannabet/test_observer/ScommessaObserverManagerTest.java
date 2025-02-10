package is.project.wannabet.test_observer;

import is.project.wannabet.model.*;
import is.project.wannabet.observer.ScommessaObserverManager;
import is.project.wannabet.repository.ScommessaRepository;
import is.project.wannabet.service.ContoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe di test per verificare il corretto funzionamento del sistema di Observer sulle scommesse.
 * Questa classe testa il comportamento del {@link ScommessaObserverManager} e la corretta gestione
 * dell'aggiornamento dello stato delle scommesse quando le quote cambiano.
 */
public class ScommessaObserverManagerTest {

    /**
     * Instanza del manager degli observer per gestire l'aggiornamento delle scommesse.
     */
    @InjectMocks
    private ScommessaObserverManager scommessaObserverManager;

    /**
     * Mock del repository delle scommesse per simulare l'accesso al database.
     */
    @Mock
    private ScommessaRepository scommessaRepository;

    /**
     * Mock del servizio per la gestione del conto degli utenti.
     */
    @Mock
    private ContoService contoService;

    /**
     * Configura i mock prima dell'esecuzione di ogni test.
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testa il caso in cui una scommessa con una quota perdente venga aggiornata.
     * <p>
     * - Crea una quota con stato {@link StatoQuota#PERDENTE}.
     * - Associa la quota a una scommessa in stato {@link StatoScommessa#DA_REFERTARE}.
     * - Simula il recupero della scommessa dal database.
     * - Dopo l'aggiornamento, la scommessa deve risultare in stato {@link StatoScommessa#PERSA}.
     */
    @Test
    public void testAggiornaScommessa_QuotePerdente() {
        // Creazione di una quota perdente
        Quota quotaPerdente = new Quota();
        quotaPerdente.setStato(StatoQuota.PERDENTE);

        // Creazione di una scommessa con una quota giocata perdente
        QuotaGiocata quotaGiocata = new QuotaGiocata();
        quotaGiocata.setQuota(quotaPerdente);

        Scommessa scommessa = new Scommessa();
        scommessa.setQuoteGiocate(List.of(quotaGiocata));
        scommessa.setStato(StatoScommessa.DA_REFERTARE);

        when(scommessaRepository.findScommesseByQuotaId(anyLong())).thenReturn(List.of(scommessa));

        // Eseguiamo l'aggiornamento della quota
        scommessaObserverManager.update(1L);

        // La scommessa deve essere persa
        assertEquals(StatoScommessa.PERSA, scommessa.getStato());
        verify(scommessaRepository, times(1)).save(scommessa);
    }

    /**
     * Testa il caso in cui una scommessa venga aggiornata con tutte le quote vincenti.
     * <p>
     * - Crea una quota con stato {@link StatoQuota#VINCENTE}.
     * - Associa la quota a una scommessa in stato {@link StatoScommessa#DA_REFERTARE}.
     * - Simula il recupero della scommessa dal database.
     * - Dopo l'aggiornamento, la scommessa deve risultare in stato {@link StatoScommessa#VINTA}.
     */
    @Test
    public void testAggiornaScommessa_TutteVincenti() {
        // Creazione di una quota vincente
        Quota quotaVincente = new Quota();
        quotaVincente.setStato(StatoQuota.VINCENTE);

        // Creazione di una scommessa con tutte le quote vincenti
        QuotaGiocata quotaGiocata = new QuotaGiocata();
        quotaGiocata.setQuota(quotaVincente);

        Scommessa scommessa = new Scommessa();
        scommessa.setQuoteGiocate(List.of(quotaGiocata));
        scommessa.setStato(StatoScommessa.DA_REFERTARE);

        when(scommessaRepository.findScommesseByQuotaId(anyLong())).thenReturn(List.of(scommessa));

        // Eseguiamo l'aggiornamento della quota
        scommessaObserverManager.update(1L);

        // La scommessa deve essere vinta
        assertEquals(StatoScommessa.VINTA, scommessa.getStato());
        verify(scommessaRepository, times(1)).save(scommessa);
    }

    /**
     * Testa il caso in cui una scommessa abbia quote miste (alcune vincenti, altre perdenti).
     * <p>
     * - Crea una quota vincente e una perdente.
     * - Associa entrambe a una scommessa in stato {@link StatoScommessa#DA_REFERTARE}.
     * - Simula il recupero della scommessa dal database.
     * - Dopo l'aggiornamento, la scommessa deve risultare in stato {@link StatoScommessa#PERSA},
     *   poiché almeno una quota è perdente.
     */
    @Test
    public void testAggiornaScommessa_QuotaMista() {
        // Creazione di una quota vincente e una perdente
        Quota quotaVincente = new Quota();
        quotaVincente.setStato(StatoQuota.VINCENTE);

        Quota quotaPerdente = new Quota();
        quotaPerdente.setStato(StatoQuota.PERDENTE);

        // Creazione di una scommessa con quote miste
        QuotaGiocata quotaGiocata1 = new QuotaGiocata();
        quotaGiocata1.setQuota(quotaVincente);

        QuotaGiocata quotaGiocata2 = new QuotaGiocata();
        quotaGiocata2.setQuota(quotaPerdente);

        Scommessa scommessa = new Scommessa();
        scommessa.setQuoteGiocate(List.of(quotaGiocata1, quotaGiocata2));
        scommessa.setStato(StatoScommessa.DA_REFERTARE);

        when(scommessaRepository.findScommesseByQuotaId(anyLong())).thenReturn(List.of(scommessa));

        // Eseguiamo l'aggiornamento della quota
        scommessaObserverManager.update(1L);

        // La scommessa deve essere persa a causa di una quota perdente
        assertEquals(StatoScommessa.PERSA, scommessa.getStato());
        verify(scommessaRepository, times(1)).save(scommessa);
    }
}
