package is.project.wannabet.test_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import is.project.wannabet.model.Evento;
import is.project.wannabet.model.Quota;
import is.project.wannabet.model.StatoQuota;
import is.project.wannabet.service.EventoService;
import is.project.wannabet.service.QuotaService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Classe di test per verificare il corretto funzionamento del controller QuotaController.
 * Testa tutte le operazioni CRUD e la gestione delle quote associate.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "admin", roles = {"ADMIN"})
public class QuotaControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuotaService quotaService;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Evento eventoDiTest;
    private Quota quotaDiTest;

    /**
     * Configura un evento di test prima di ogni esecuzione.
     */
    @BeforeAll
    public void setup() {
        eventoDiTest = eventoService.createEvento("Sinner vs Medvedev", new Date(), "", "Tennis");

        eventoService.flush();
        eventoDiTest = eventoService.getEventoById(eventoDiTest.getIdEvento()).get();
        assertNotNull(eventoDiTest.getIdEvento()); // Assicura che l'evento abbia un ID valido

        quotaDiTest = quotaService.createQuota("1", "Risultato Finale", 2.5, eventoDiTest);
        assertNotNull(quotaDiTest.getIdQuota()); // Assicura che la quota abbia un ID valido
    }




    /**
     * Testa la creazione di una quota tramite il controller.
     */
    @Test
    public void testCreateQuota() throws Exception {
        Quota nuovaQuota = new Quota();
        nuovaQuota.setMoltiplicatore(2.5);
        nuovaQuota.setEsito("1");
        nuovaQuota.setChiusa(false);
        nuovaQuota.setCategoria("Risultato Finale");
        nuovaQuota.setEvento(eventoDiTest);
        nuovaQuota.setStato(StatoQuota.DA_REFERTARE);

        System.out.println("JSON inviato: " + objectMapper.writeValueAsString(nuovaQuota));



        mockMvc.perform(post("/api/quota/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuovaQuota)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.moltiplicatore").value(2.5));
    }

    /**
     * Testa il recupero di tutte le quote.
     */
    @Test
    public void testGetAllQuote() throws Exception {
        mockMvc.perform(get("/api/quota/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    /**
     * Testa il recupero delle quote associate a un evento.
     */
    @Test
    public void testGetQuoteByEvento() throws Exception {
        mockMvc.perform(get("/api/quota/evento/" + eventoDiTest.getIdEvento()))
                .andExpect(status().isOk());
    }

    /**
     * Testa la refertazione di una quota.
     */
    @Test
    public void testRefertaQuota() throws Exception {


        mockMvc.perform(post("/api/quota/referta/" + quotaDiTest.getIdQuota())
                        .param("referto", "1"))
                .andExpect(status().isOk());
    }

    /**
     * Testa l'eliminazione di una quota.
     */
    @Test
    public void testDeleteQuota() throws Exception {

        mockMvc.perform(delete("/api/quota/delete/" + quotaDiTest.getIdQuota()))
                .andExpect(status().isOk());
    }

    /**
     * Testa la chiusura di una quota e verifica che mantenga il suo stato.
     */
    @Test
    public void testChiusuraQuota() throws Exception {

        Quota quota = quotaService.getQuotaById(quotaDiTest.getIdQuota()).get();

        quota.setChiusa(true);



        mockMvc.perform(put("/api/quota/update/" + quotaDiTest.getIdQuota())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quota)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chiusa").value(true));
    }

    /**
     * Testa la modifica di una quota.
     */
    @Test
    public void testUpdateQuota() throws Exception {
        Quota quota = new Quota();
        quota.setMoltiplicatore(2.5);
        quota.setEsito("1");
        quota.setStato(StatoQuota.VINCENTE);
        quota.setCategoria("Risultato Finale");
        quota.setEvento(eventoDiTest);

        quota.setMoltiplicatore(3.0); // Modifica il valore della quota

        mockMvc.perform(put("/api/quota/update/" + quotaDiTest.getIdQuota())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quota)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.moltiplicatore").value(3.0));
    }
}

