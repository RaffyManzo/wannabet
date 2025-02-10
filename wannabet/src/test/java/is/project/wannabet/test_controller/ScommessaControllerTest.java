package is.project.wannabet.test_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import is.project.wannabet.WannabetApplication;
import is.project.wannabet.factory.ScommessaFactory;
import is.project.wannabet.model.*;
import is.project.wannabet.service.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test per ScommessaController.
 */
@SpringBootTest(classes = WannabetApplication.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ScommessaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private QuotaService quotaService;

    @Autowired
    private ContoService contoService;

    @Autowired
    private AccountRegistratoService accountService;

    @Autowired
    private ScommessaService scommessaService;

    private AccountRegistrato account;
    private Quota quota;

    @BeforeEach
    public void setup() {
        Evento evento = eventoService.createEvento("evento1", new Date(), "descrizione", "Calcio");
        quota = quotaService.createQuota("1", "Risultato finale", 1.70, evento);

        Conto conto = new Conto();
        conto.setSaldo(500.00);
        conto.setDataCreazione(new Date());
        conto.setIndirizzoFatturazione("Via Roma, 10");
        conto = contoService.saveConto(conto);

        account = new AccountRegistrato();
        account.setCodiceFiscale("XYZ12345");
        account.setNome("Mario");
        account.setCognome("Rossi");
        account.setConto(conto);
        account.setTipo(TipoAccount.UTENTE);
        account.setEmail("mario.rossi@example.com");
        account = accountService.saveAccount(account);
    }

    @AfterEach
    public void cleanup() {
        quotaService.deleteQuota(quota.getIdQuota());
        eventoService.deleteEvento(quota.getEvento().getIdEvento());
        accountService.deleteAccount(account.getIdAccount());
        contoService.deleteConto(account.getConto().getIdConto());
    }

    @Test
    public void testCreateScommessa() throws Exception {
        Scommessa nuovaScommessa = ScommessaFactory.createScommessa(account, List.of(quota), 100);

        mockMvc.perform(post("/api/scommesse/" + account.getIdAccount() + "/crea")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuovaScommessa)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetScommessaById() throws Exception {
        Scommessa scommessa = scommessaService.saveScommessa(ScommessaFactory.createScommessa(account, List.of(quota), 100));

        mockMvc.perform(get("/api/scommesse/account/" + account.getIdAccount() + "/get/" + scommessa.getIdScommessa())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
