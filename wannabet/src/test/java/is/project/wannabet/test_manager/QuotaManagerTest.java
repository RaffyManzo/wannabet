package is.project.wannabet.test_manager;

import is.project.wannabet.WannabetApplication;
import is.project.wannabet.controller.QuotaManager;
import is.project.wannabet.factory.EventoFactory;
import is.project.wannabet.factory.QuotaFactory;
import is.project.wannabet.model.Evento;
import is.project.wannabet.model.Quota;
import is.project.wannabet.model.StatoQuota;
import is.project.wannabet.observer.QuotaObserver;
import is.project.wannabet.service.EventoService;
import is.project.wannabet.service.QuotaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = WannabetApplication.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WithMockUser(username = "testuser", roles = {"USER"})
class QuotaManagerTest {

    private QuotaManager quotaManager;
    private Quota quota;
    private Evento evento;

    @Autowired
    private QuotaService quotaService;

    @Autowired
    private EventoService eventoService;

    @BeforeEach
    void setUp() {
        quotaManager = QuotaManager.getInstance(); // Otteniamo il Singleton
        quotaManager.reset(); // Pulisce le quote registrate prima di ogni test

        evento = eventoService.createEvento("Test Evento", new Date(), "Test Descrizione", "Calcio");
        quota = quotaService.createQuota("1", "Risultato finale", 2.50, evento);
    }

    @Test
    void testAggiuntaQuota() {
        quotaManager.aggiornaQuota(quota);

        Quota retrievedQuota = quotaManager.getQuota(quota.getIdQuota());
        assertNotNull(retrievedQuota, "La quota dovrebbe essere registrata nel QuotaManager");
        assertEquals(quota.getIdQuota(), retrievedQuota.getIdQuota(), "L'ID della quota deve corrispondere");
    }

    @Test
    void testAggiornamentoQuota() {
        quotaManager.aggiornaQuota(quota);

        // Modifichiamo la quota e aggiorniamo
        quota.setMoltiplicatore(3.00);
        quotaManager.aggiornaQuota(quota);

        Quota updatedQuota = quotaManager.getQuota(quota.getIdQuota());
        assertNotNull(updatedQuota, "La quota dovrebbe essere ancora presente");
        assertEquals(3.00, updatedQuota.getMoltiplicatore(), "Il moltiplicatore dovrebbe essere stato aggiornato");
    }

    @Test
    void testRimozioneQuota() {
        quotaManager.aggiornaQuota(quota);
        quotaManager.rimuoviQuota(quota.getIdQuota());

        Quota removedQuota = quotaManager.getQuota(quota.getIdQuota());
        assertNull(removedQuota, "La quota dovrebbe essere stata rimossa dal QuotaManager");
    }

    @Test
    void testRegistrazioneObserver() {
        AtomicBoolean observerNotified = new AtomicBoolean(false);

        QuotaObserver observer = quota -> observerNotified.set(true);
        quotaManager.addObserver(observer);

        quotaManager.aggiornaQuota(quota);

        assertTrue(observerNotified.get(), "L'observer dovrebbe essere stato notificato");
    }

    @Test
    void testNotificaObserverSuRefertazione() {
        AtomicBoolean observerNotified = new AtomicBoolean(false);

        QuotaObserver observer = quota -> observerNotified.set(true);
        quotaManager.addObserver(observer);

        // Cambiamo lo stato della quota
        quota.setStato(StatoQuota.VINCENTE);
        quotaManager.aggiornaQuota(quota);

        assertTrue(observerNotified.get(), "L'observer dovrebbe essere notificato dopo una refertazione");
    }
}
