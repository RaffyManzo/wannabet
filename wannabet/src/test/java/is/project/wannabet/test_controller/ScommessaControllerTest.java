package is.project.wannabet.test_controller;

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
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Classe di test per verificare il corretto funzionamento del controller ScommessaController.
 * Testa la creazione, il recupero delle scommesse e la gestione delle quote giocate.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
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


    private AccountRegistrato accountMock;
    private Conto contoMock;
    private Evento eventoDiTest;
    private Quota quotaDiTest;
    private Scommessa scommessaDiTest;

    @Mock
    private ContoService contoService;

    @Mock
    private AccountRegistratoService accountService;

    @Mock
    private SaldoFedeltaService saldoFedeltaService;

    @BeforeAll
    public void setup() {
        // 1️⃣ Creazione Saldo Fedeltà
        SaldoFedelta saldoFedelta = new SaldoFedelta();
        saldoFedelta.setPunti(100);
        saldoFedelta = saldoFedeltaService.saveSaldoFedelta(saldoFedelta);

        saldoFedeltaService.flush();
        assertNotNull(saldoFedelta.getIdSaldoFedelta());

        // 2️⃣ Creazione Conto
        contoMock = new Conto();
        contoMock.setSaldo(1000.0);
        contoMock = contoService.saveConto(contoMock);

        contoService.flush();
        assertNotNull(contoMock.getIdConto());

        // 3️⃣ Creazione Account
        accountMock = new AccountRegistrato();
        accountMock.setSaldoFedelta(saldoFedelta);
        accountMock.setNome("Pippo");
        accountMock.setCognome("Baudo");
        accountMock.setDataNascita(new Date());
        accountMock.setCodiceFiscale("XYZ1234");
        accountMock.setTipo(TipoAccount.UTENTE);
        accountMock.setEmail("test@email.com");
        accountMock.setConto(contoMock);
        accountMock = accountService.saveAccount(accountMock);
        accountService.flush();
        assertNotNull(accountMock.getIdAccount());

        // 4️⃣ Creazione Evento
        eventoDiTest = eventoService.createEvento("Sinner vs Medvedev", new Date(), "", "Tennis");
        eventoService.flush();
        eventoDiTest = eventoService.getEventoById(eventoDiTest.getIdEvento()).get();
        assertNotNull(eventoDiTest.getIdEvento());

        // 5️⃣ Creazione Quota
        quotaDiTest = quotaService.createQuota("1", "Risultato Finale", 2.5, eventoDiTest);
        quotaService.flush();
        assertNotNull(quotaDiTest.getIdQuota());

        // 6️⃣ Creazione Scommessa
        scommessaDiTest = scommessaService.creaScommessa(List.of(quotaDiTest), 100.0, accountMock.getIdAccount());
        assertNotNull(scommessaDiTest.getIdScommessa());
    }

    /**
     * Testa la creazione di una nuova scommessa.
     */
    @Test
    public void testCreateScommessa() throws Exception {
        Scommessa nuovaScommessa = new Scommessa();
        nuovaScommessa.setImporto(100.0);

        mockMvc.perform(post("/api/scommesse/"+accountMock.getIdAccount()+"/crea")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuovaScommessa)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.importo").value(100.0));
    }

    /**
     * Testa il recupero di tutte le scommesse per un account.
     */
    @Test
    public void testGetScommesseByAccount() throws Exception {
        mockMvc.perform(get("/api/scommesse/account/" + accountMock.getIdAccount()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    /**
     * Testa il recupero di una scommessa specifica.
     */
    @Test
    public void testGetScommessaById() throws Exception {
        mockMvc.perform(get("/api/scommesse/account/"+accountMock.getIdAccount()+"/get/" + scommessaDiTest.getIdScommessa()))
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
