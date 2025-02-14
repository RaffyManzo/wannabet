package is.project.wannabet.test_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.model.Conto;
import is.project.wannabet.model.SaldoFedelta;
import is.project.wannabet.model.TipoAccount;
import is.project.wannabet.service.AccountRegistratoService;
import is.project.wannabet.service.ContoService;
import is.project.wannabet.service.SaldoFedeltaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static is.project.wannabet.security.CustomPasswordEncoder.sha256;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "testC@email.com", roles = {"UTENTE"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContoService contoService;

    @Autowired
    private AccountRegistratoService accountService;

    @Autowired
    private SaldoFedeltaService saldoFedeltaService;

    private AccountRegistrato accountDiTest;
    private Conto contoDiTest;

    @Autowired
    private ObjectMapper objectMapper;

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
        contoDiTest.setSaldo(100.0);
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
        accountDiTest.setCodiceFiscale("HFUEHFR4665");
        accountDiTest.setTipo(TipoAccount.UTENTE);
        accountDiTest.setEmail("testC@email.com");
        accountDiTest.setConto(contoDiTest);
        accountDiTest.setPassword(sha256("abcde"));
        accountDiTest = accountService.saveAccount(accountDiTest);
        accountService.flush();
        assertNotNull(accountDiTest.getIdAccount());
    }



    @Test
    public void testVerificaSaldoSufficiente() throws Exception {
        mockMvc.perform(get("/api/conto/" + accountDiTest.getIdAccount() + "/verifica-saldo")
                        .param("importo", "50"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testVerificaSaldoInsufficiente() throws Exception {
        mockMvc.perform(get("/api/conto/" + accountDiTest.getIdAccount() + "/verifica-saldo")
                        .param("importo", "200"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    public void testDeposita() throws Exception {
        mockMvc.perform(post("/api/conto/" + accountDiTest.getIdAccount() + "/deposita")
                        .param("importo", "50"))
                .andExpect(status().isOk());

        assertTrue(contoService.verificaSaldo(accountDiTest.getIdAccount(), 150.0));
    }

    @Test
    public void testPreleva() throws Exception {

        contoDiTest.setSaldo(100.0);
        contoService.saveConto(contoDiTest);


        mockMvc.perform(post("/api/conto/" + accountDiTest.getIdAccount() + "/preleva")
                        .param("importo", "50"))
                .andExpect(status().isOk());

        assertTrue(contoService.verificaSaldo(accountDiTest.getIdAccount(), 50.0));
    }

    @Test
    public void testPrelievoInsufficiente() throws Exception {
        mockMvc.perform(post("/api/conto/" + accountDiTest.getIdAccount() + "/preleva")
                        .param("importo", "200"))
                .andExpect(status().isBadRequest());
    }
}
