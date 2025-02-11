package is.project.wannabet.test_observer;

import is.project.wannabet.cache.QuotaCache;
import is.project.wannabet.model.Quota;
import is.project.wannabet.model.StatoQuota;
import is.project.wannabet.observer.QuotaManager;
import is.project.wannabet.observer.ScommessaObserverManager;
import is.project.wannabet.service.QuotaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

/**
 * Test per verificare il corretto funzionamento del sistema di Observer per le quote.
 */
@SpringBootTest
@Transactional
public class QuotaObserverTest {

    @Autowired
    private QuotaManager quotaManager;

    @Autowired
    private QuotaCache quotaCache;

    @Autowired
    private QuotaService quotaService;

    @Mock
    private ScommessaObserverManager scommessaObserverManager;

    private Quota quotaDiTest;

    /**
     * Configura una quota di test prima di ogni esecuzione.
     */
    @BeforeEach
    public void setup() {
        quotaDiTest = new Quota();
        quotaDiTest.setMoltiplicatore(2.5);
        quotaDiTest.setEsito("1");
        quotaDiTest.setCategoria("Risultato Finale");
        quotaDiTest.setChiusa(false);
        quotaDiTest.setStato(StatoQuota.DA_REFERTARE);
        quotaDiTest = quotaService.saveQuota(quotaDiTest);

        // Registra il mock dell'Observer
        quotaManager.addObserver(scommessaObserverManager);
    }

    /**
     * Testa che una quota venga correttamente salvata nella cache.
     */
    @Test
    public void testQuotaSalvataInCache() {
        quotaCache.aggiornaQuota(quotaDiTest);
        Quota quotaRecuperata = quotaCache.getQuota(quotaDiTest.getIdQuota());

        assertNotNull(quotaRecuperata);
        assertEquals(quotaDiTest.getMoltiplicatore(), quotaRecuperata.getMoltiplicatore());
    }

    /**
     * Testa che l'Observer venga notificato quando cambia una quota.
     */
    @Test
    public void testObserverNotificatoSuAggiornamentoQuota() {
        quotaDiTest.setMoltiplicatore(3.0);
        quotaManager.aggiornaQuota(quotaDiTest);

        // Verifica che l'Observer sia stato notificato
        verify(scommessaObserverManager, times(1)).update(quotaDiTest.getIdQuota());
    }

    /**
     * Testa che la quota aggiornata sia presente nella cache.
     */
    @Test
    public void testQuotaAggiornataInCache() {
        quotaDiTest.setMoltiplicatore(3.0);
        quotaManager.aggiornaQuota(quotaDiTest);

        Quota quotaRecuperata = quotaCache.getQuota(quotaDiTest.getIdQuota());
        assertNotNull(quotaRecuperata);
        assertEquals(3.0, quotaRecuperata.getMoltiplicatore());
    }
}
