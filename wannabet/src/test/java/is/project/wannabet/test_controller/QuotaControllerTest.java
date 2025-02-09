package is.project.wannabet.test_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import is.project.wannabet.WannabetApplication;
import is.project.wannabet.controller.QuotaController;
import is.project.wannabet.factory.EventoFactory;
import is.project.wannabet.factory.QuotaFactory;
import is.project.wannabet.model.Evento;
import is.project.wannabet.model.Quota;
import is.project.wannabet.model.StatoQuota;
import is.project.wannabet.repository.EventoRepository;
import is.project.wannabet.repository.QuotaRepository;
import is.project.wannabet.service.QuotaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *  Test completo del modulo `Quota`, utilizzando un database H2 in memoria.
 * 🔹 Nessun mock (Mockito), test realistico con repository veri.
 */
@SpringBootTest(classes = WannabetApplication.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WithMockUser(username = "testuser", roles = {"USER"})
public class QuotaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private QuotaService quotaService;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private QuotaRepository quotaRepository;

    private Evento evento;

    @BeforeEach
    public void setUp() {
        //  Pulisce il database prima di ogni test
        quotaRepository.deleteAll();
        eventoRepository.deleteAll();

        //  Crea un evento fittizio per i test
        evento = eventoRepository.save(EventoFactory.createEvento("evento1", new Date(), "des", "Calcio"));
    }

    /**
     *  Test: Creazione e recupero di una quota senza mocking
     */
    @Test
    public void testCreateAndRetrieveQuota() throws Exception {
        //  Creazione e salvataggio della quota
        Quota quota = QuotaFactory.createQuota(evento, 1.70, "1", "Risultato finale");
        quota = quotaRepository.save(quota);

        //  Test recupero quota
        mockMvc.perform(get("/api/quota/" + quota.getIdQuota())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.esito", is("1")))
                .andExpect(jsonPath("$.stato", is("DA_REFERTARE")));
    }

    /**
     *  Test: Refertazione di una quota vincente
     */
    @Test
    public void testRefertaQuotaVincente() throws Exception {
        Quota quota = QuotaFactory.createQuota(evento, 1.70, "1", "Risultato finale");
        quota = quotaRepository.save(quota);

        //  Referta la quota come vincente
        mockMvc.perform(post("/api/quota/referta/" + quota.getIdQuota())
                        .param("referto", "1") // Il referto combacia con l'esito della quota
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //  Controlla che la quota ora sia vincente
        Quota quotaAggiornata = quotaRepository.findById(quota.getIdQuota()).orElseThrow();
        assert quotaAggiornata.getStato() == StatoQuota.VINCENTE;
    }

    /**
     * Test: Refertazione di una quota perdente
     */
    @Test
    public void testRefertaQuotaPerdente() throws Exception {
        Quota quota = QuotaFactory.createQuota(evento, 1.70, "1", "Risultato finale");
        quota = quotaRepository.save(quota);

        // Referta la quota con un esito errato
        mockMvc.perform(post("/api/quota/referta/" + quota.getIdQuota())
                        .param("referto", "X") // Esito errato
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Controlla che la quota ora sia perdente
        Quota quotaAggiornata = quotaRepository.findById(quota.getIdQuota()).orElseThrow();
        assert quotaAggiornata.getStato() == StatoQuota.PERDENTE;
    }



    /**
     * Test: Prova a refertare una quota inesistente (404 Not Found)
     */
    @Test
    public void testRefertaQuotaNonEsistente() throws Exception {
        mockMvc.perform(post("/api/quota/referta/999") // ID non esistente
                        .param("referto", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     *  Test: Eliminazione di una quota
     */
    @Test
    public void testDeleteQuota() throws Exception {
        Quota quota = QuotaFactory.createQuota(evento, 1.70, "1", "Risultato finale");
        quota = quotaRepository.save(quota);

        //  Elimina la quota
        mockMvc.perform(delete("/api/quota/" + quota.getIdQuota())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //  Verifica che la quota sia stata effettivamente eliminata
        assert quotaRepository.findById(quota.getIdQuota()).isEmpty();
    }
}
