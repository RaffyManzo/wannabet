package is.project.wannabet.test_controller;

import is.project.wannabet.model.*;
import is.project.wannabet.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ui.Model;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Classe di test per verificare il corretto funzionamento del controller ScontrinoController.
 * Testa la creazione dello scontrino, l'aggiunta e rimozione di quote, e la conferma della scommessa.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ScontrinoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuotaService quotaService;

    @Autowired
    private ContoService contoService;

    @Autowired
    private AccountRegistratoService accountService;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private ScommessaService scommessaService;

    @Autowired
    private SaldoFedeltaService saldoFedeltaService;

    @Autowired
    private ObjectMapper objectMapper;

    private AccountRegistrato accountDiTest;
    private Conto contoDiTest;
    private Evento eventoDiTest;
    private Quota quotaDiTest;
    private MockHttpSession session;

    @BeforeEach
    public void setup() {
        session = new MockHttpSession();
        // 1️⃣ Crea e salva Saldo Fedeltà
        SaldoFedelta saldoFedelta = new SaldoFedelta();
        saldoFedelta.setPunti(100);
        saldoFedelta = saldoFedeltaService.saveSaldoFedelta(saldoFedelta);
        saldoFedeltaService.flush();
        assertNotNull(saldoFedelta.getIdSaldoFedelta());

        // 2️⃣ Crea e salva Conto
        contoDiTest = new Conto();
        contoDiTest.setSaldo(1000.0);
        contoDiTest.setDataCreazione(new Date());
        contoDiTest.setIndirizzoFatturazione("Via .");
        contoDiTest = contoService.saveConto(contoDiTest);
        contoService.flush();
        assertNotNull(contoDiTest.getIdConto());

        // 3️⃣ Crea e salva Account
        accountDiTest = new AccountRegistrato();
        accountDiTest.setSaldoFedelta(saldoFedelta);
        accountDiTest.setNome("Pippo");
        accountDiTest.setCognome("Baudo");
        accountDiTest.setDataNascita(new Date());
        accountDiTest.setCodiceFiscale("XYZ1234");
        accountDiTest.setTipo(TipoAccount.UTENTE);
        accountDiTest.setEmail("test@email.com");
        accountDiTest.setConto(contoDiTest);
        accountDiTest = accountService.saveAccount(accountDiTest);
        accountService.flush();
        assertNotNull(accountDiTest.getIdAccount());

        // 4️⃣ Crea e salva Evento
        eventoDiTest = eventoService.createEvento("Sinner vs Medvedev", new Date(), "", "Tennis");
        eventoService.flush();
        eventoDiTest = eventoService.getEventoById(eventoDiTest.getIdEvento()).get();
        assertNotNull(eventoDiTest.getIdEvento());

        // 5️⃣ Crea e salva Quota
        quotaDiTest = quotaService.createQuota("1", "Risultato Finale", 2.5, eventoDiTest);
        quotaService.flush();
        assertNotNull(quotaDiTest.getIdQuota());
    }

    /**
     * Testa la creazione automatica dello scontrino in sessione.
     */
    @Test
    public void testGetScontrino() throws Exception {
        mockMvc.perform(get("/api/scontrino").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quote").isArray());
    }

    /**
     * Testa l'aggiunta di una quota valida allo scontrino.
     */
    @Test
    public void testAggiungiQuota() throws Exception {
        mockMvc.perform(post("/api/scontrino/aggiungi/" + quotaDiTest.getIdQuota()).session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quote", hasSize(1)));
    }

    /**
     * Testa la conferma di una scommessa con quote valide.
     */
    @Test
    public void testConfermaScommessa() throws Exception {
        mockMvc.perform(post("/api/scontrino/aggiungi/" + quotaDiTest.getIdQuota()).session(session));

        mockMvc.perform(post("/api/scontrino/conferma")
                        .param("importo", "100")
                        .param("idAccount", accountDiTest.getIdAccount().toString())
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(content().string("Scommessa effettuata con successo!"));
    }

    /**
     * Testa la conferma di una scommessa con quote chiuse.
     */
    @Test
    public void testConfermaScommessaConQuoteChiuse() throws Exception {
        quotaDiTest.setChiusa(true);
        quotaService.saveQuota(quotaDiTest);

        mockMvc.perform(post("/api/scontrino/aggiungi/" + quotaDiTest.getIdQuota()).session(session));

        mockMvc.perform(post("/api/scontrino/conferma")
                        .param("importo", "100")
                        .param("idAccount", accountDiTest.getIdAccount().toString())
                        .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Lo scontrino contiene quote chiuse."));
    }
}
