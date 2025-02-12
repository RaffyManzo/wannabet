package is.project.wannabet.test_controller;

import is.project.wannabet.factory.ScommessaFactory;
import is.project.wannabet.model.*;
import is.project.wannabet.service.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static is.project.wannabet.security.PasswordEncoding.sha256;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Classe di test per verificare il corretto funzionamento del controller ScommessaController.
 * Testa la creazione, il recupero delle scommesse e la gestione delle quote giocate.
 */


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "user", roles = {"UTENTE"})

public class ScommessaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ScommessaService scommessaService;

    @Autowired
    private QuotaGiocataService quotaGiocataService;

    @Autowired
    private QuotaService quotaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private ContoService contoService;

    @Autowired
    private AccountRegistratoService accountService;

    @Autowired
    private SaldoFedeltaService saldoFedeltaService;

    private AccountRegistrato accountDiTest;
    private Conto contoDiTest;
    private Evento eventoDiTest;
    private Quota quotaDiTest;
    private Scommessa scommessaDiTest;

    @BeforeEach
    public void setup() {
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
        accountDiTest.setPassword(sha256("abcde"));
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

        // 6️⃣ Crea e salva Scommessa
        scommessaDiTest = scommessaService.creaScommessa(List.of(quotaDiTest), 100.0, accountDiTest.getIdAccount());
        assertNotNull(scommessaDiTest.getIdScommessa());
    }

    /**
     * Testa la creazione di una nuova scommessa.
     */
    @Test
    public void testCreateScommessa() throws Exception {
        Scommessa nuovaScommessa = ScommessaFactory.createScommessa(accountDiTest, List.of(quotaDiTest), 100);

        mockMvc.perform(post("/api/scommesse/" + accountDiTest.getIdAccount() + "/crea")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuovaScommessa)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.importo").value(100.0));
    }


    /**
     * Testa la creazione di una scommessa con credito insufficiente.
     */
    @Test
    public void testCreateScommessaWithInsufficientCredit() throws Exception {
        Scommessa nuovaScommessa = ScommessaFactory.createScommessa(accountDiTest, List.of(quotaDiTest), 2000);


        mockMvc.perform(post("/api/scommesse/" + accountDiTest.getIdAccount() + "/crea")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuovaScommessa)))
                .andExpect(status().isBadRequest());

    }

    /**
     * Testa il recupero di tutte le scommesse per un account.
     */
    @Test
    public void testGetScommesseByAccount() throws Exception {
        mockMvc.perform(get("/api/scommesse/account/" + accountDiTest.getIdAccount()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    /**
     * Testa il recupero di una scommessa specifica.
     */
    @Test
    public void testGetScommessaById() throws Exception {
        mockMvc.perform(get("/api/scommesse/account/" + accountDiTest.getIdAccount() + "/get/" + scommessaDiTest.getIdScommessa()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_scommessa").value(scommessaDiTest.getIdScommessa()));
    }

    /**
     * Testa il recupero delle quote giocate in una scommessa.
     */
    @Test
    public void testGetQuoteGiocateByScommessa() throws Exception {
        mockMvc.perform(get("/api/quota_giocata/scommessa/" + scommessaDiTest.getIdScommessa()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }
}
