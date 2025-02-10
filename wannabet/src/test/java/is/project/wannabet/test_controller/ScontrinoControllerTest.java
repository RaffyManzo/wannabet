package is.project.wannabet.test_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import is.project.wannabet.WannabetApplication;
import is.project.wannabet.model.*;
import is.project.wannabet.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Classe di test per il controller dello scontrino
 */
@SpringBootTest(classes = WannabetApplication.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WithMockUser(username = "testuser", roles = {"USER"})
public class ScontrinoControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private QuotaService quotaService;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private SaldoFedeltaService saldoFedeltaService;

    @Autowired
    private ContoService contoService;

    @Autowired
    private AccountRegistratoService accountRegistratoService;

    private MockHttpSession session;
    private Quota quotaValida;
    private Quota quotaChiusa;

    private AccountRegistrato accountRegistrato;

    @BeforeEach
    public void setup() {
        session = new MockHttpSession();

        // Creazione evento
        Evento evento = eventoService.createEvento("evento1", new Date(), "descrizione", "Calcio");

        accountRegistrato = getAccountCorrente();

        // Creazione di quote di test
        quotaValida = quotaService.createQuota("X", "Risultato finale", 3.00, evento, false);
        quotaChiusa = quotaService.createQuota("1", "Risultato finale", 1.56, evento, true);
    }

    /**
     * Test: recupero dello stato iniziale dello scontrino.
     */
    @Test
    public void testGetScontrinoVuoto() throws Exception {
        mockMvc.perform(get("/api/scontrino").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quote", hasSize(0)))
                .andReturn();
    }

    /**
     * Test: aggiunta di una quota valida allo scontrino.
     */
    @Test
    public void testAggiungiQuotaScontrino() throws Exception {
        // Aggiunge una quota
        mockMvc.perform(post("/api/scontrino/aggiungi/" + quotaValida.getIdQuota())
                        .session(session))
                .andExpect(status().isOk())
                .andReturn();

        // Recupera lo scontrino e verifica la presenza della quota
        mockMvc.perform(get("/api/scontrino").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quote", hasSize(1)))
                .andExpect(jsonPath("$.quote[0].esito", is("X")))
                .andReturn();
    }

    /**
     * Test: tentativo di aggiungere una quota chiusa.
     */
    @Test
    public void testAggiungiQuotaChiusaScontrino() throws Exception {
        mockMvc.perform(post("/api/scontrino/aggiungi/" + quotaChiusa.getIdQuota()).session(session))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    /**
     * Test: rimozione di una quota dallo scontrino.
     */
    @Test
    public void testRimuoviQuotaScontrino() throws Exception {
        // Aggiunge una quota
        mockMvc.perform(post("/api/scontrino/aggiungi/" + quotaValida.getIdQuota()).session(session))
                .andExpect(status().isOk())
                .andReturn();

        // Rimuove la quota
        mockMvc.perform(delete("/api/scontrino/rimuovi/" + quotaValida.getIdQuota()).session(session))
                .andExpect(status().isOk())
                .andReturn();

        // Verifica che lo scontrino sia vuoto
        mockMvc.perform(get("/api/scontrino").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quote", hasSize(0)))
                .andReturn();
    }

    /**
     * Test: svuotare lo scontrino.
     */
    @Test
    public void testSvuotaScontrino() throws Exception {
        // Aggiunge una quota
        mockMvc.perform(post("/api/scontrino/aggiungi/" + quotaValida.getIdQuota()).session(session))
                .andExpect(status().isOk())
                .andReturn();

        // Svuota lo scontrino
        mockMvc.perform(delete("/api/scontrino/svuota").session(session))
                .andExpect(status().isOk())
                .andReturn();

        // Verifica che lo scontrino sia vuoto
        mockMvc.perform(get("/api/scontrino").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quote", hasSize(0)))
                .andReturn();
    }

    /**
     * Test: conferma di una scommessa con saldo sufficiente.
     */
    @Test
    public void testConfermaScommessa() throws Exception {
        // Aggiunge una quota
        mockMvc.perform(post("/api/scontrino/aggiungi/" + quotaValida.getIdQuota()).session(session))
                .andExpect(status().isOk())
                .andReturn();

        // Conferma la scommessa
        mockMvc.perform(post("/api/scontrino/conferma")
                        .param("importo", "100")
                        .param("idAccount", ""+accountRegistrato.getIdAccount())
                        .session(session))
                .andExpect(status().isOk())
                .andReturn();

        // Verifica che lo scontrino sia stato svuotato
        mockMvc.perform(get("/api/scontrino").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quote", hasSize(0)))
                .andReturn();
    }

    /**
     * Test: conferma di una scommessa con saldo insufficiente.
     */
    @Test
    public void testConfermaScommessaSaldoInsufficiente() throws Exception {
        // Aggiunge una quota
        mockMvc.perform(post("/api/scontrino/aggiungi/" + quotaValida.getIdQuota()).session(session))
                .andExpect(status().isOk())
                .andReturn();

        // Tentativo di conferma con saldo insufficiente
        mockMvc.perform(post("/api/scontrino/conferma")
                        .param("importo", "1000") // Supera il saldo disponibile
                        .param("idAccount", ""+accountRegistrato.getIdAccount())
                        .session(session))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    private AccountRegistrato getAccountCorrente() {

        // Creazione del conto e salvataggio nel database
        Conto conto = new Conto();
        conto.setSaldo(500.00);
        conto.setDataCreazione(new Date());
        conto.setIndirizzoFatturazione("Via Roma, 10");
        conto = contoService.saveConto(conto);

        SaldoFedelta saldoFedelta = new SaldoFedelta();
        saldoFedelta.setPunti(10);
        saldoFedelta = saldoFedeltaService.saveSaldoFedelta(saldoFedelta);

        // Creazione di un account senza impostare manualmente l'ID
        AccountRegistrato ac = new AccountRegistrato();
        ac.setCodiceFiscale("XYZ12345");  // Devi settare campi validi
        ac.setNome("Mario");
        ac.setCognome("Rossi");
        ac.setSaldoFedelta(saldoFedelta);
        ac.setConto(conto);
        ac.setTipo(TipoAccount.UTENTE);
        ac.setEmail("mario.rossi@example.com");
        ac = accountRegistratoService.saveAccount(ac);


        // TODO: Recuperare l'account dalla sessione o dal contesto di Spring Security
        return ac; // Placeholder temporaneo
    }

    /**
     * Test: conferma con quota chiusa.
     */
    @Test
    public void testConfermaScommessaQuotaChiusa() throws Exception {
        // Aggiunge una quota chiusa
        mockMvc.perform(post("/api/scontrino/aggiungi/" + quotaChiusa.getIdQuota()).session(session))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Tentativo di conferma scommessa
        mockMvc.perform(post("/api/scontrino/conferma")
                        .param("importo", "100")
                        .param("idAccount", ""+accountRegistrato.getIdAccount())
                        .session(session))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
