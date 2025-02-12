package is.project.wannabet.test_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.model.Conto;
import is.project.wannabet.model.SaldoFedelta;
import is.project.wannabet.model.TipoAccount;
import is.project.wannabet.repository.AccountRegistratoRepository;
import is.project.wannabet.service.AccountRegistratoService;
import is.project.wannabet.service.ContoService;
import is.project.wannabet.service.SaldoFedeltaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static is.project.wannabet.security.PasswordEncoding.sha256;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountRegistratoRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SaldoFedeltaService saldoFedeltaService;

    @Autowired
    private ContoService contoService;

    @Autowired
    private AccountRegistratoService accountService;

    private AccountRegistrato adminUser;
    private AccountRegistrato normalUser1;
    private AccountRegistrato normalUser2;
    private Conto contoDiTest;

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

        // 3️⃣ Crea e salva un utente normale (User 1)
        normalUser1 = new AccountRegistrato();
        normalUser1.setSaldoFedelta(saldoFedelta);
        normalUser1.setNome("Pippo");
        normalUser1.setCognome("Baudo");
        normalUser1.setDataNascita(new Date());
        normalUser1.setCodiceFiscale("XYZ1234");
        normalUser1.setTipo(TipoAccount.UTENTE);
        normalUser1.setEmail("user1@email.com");
        normalUser1.setConto(contoDiTest);
        normalUser1.setPassword(sha256("password123"));
        normalUser1 = accountService.saveAccount(normalUser1);
        accountService.flush();
        assertNotNull(normalUser1.getIdAccount());

        // 4️⃣ Crea e salva un secondo utente normale (User 2)
        normalUser2 = new AccountRegistrato();
        normalUser2.setSaldoFedelta(saldoFedelta);
        normalUser2.setNome("Mario");
        normalUser2.setCognome("Rossi");
        normalUser2.setDataNascita(new Date());
        normalUser2.setCodiceFiscale("XYZ5678");
        normalUser2.setTipo(TipoAccount.UTENTE);
        normalUser2.setEmail("user2@email.com");
        normalUser2.setConto(contoDiTest);
        normalUser2.setPassword(sha256("password456"));
        normalUser2 = accountService.saveAccount(normalUser2);
        accountService.flush();
        assertNotNull(normalUser2.getIdAccount());

        // 5️⃣ Crea e salva un admin
        adminUser = new AccountRegistrato();
        adminUser.setSaldoFedelta(saldoFedelta);
        adminUser.setNome("Admin");
        adminUser.setCognome("Super");
        adminUser.setDataNascita(new Date());
        adminUser.setCodiceFiscale("XYZ9999");
        adminUser.setTipo(TipoAccount.ADMIN);
        adminUser.setEmail("admin@email.com");
        adminUser.setConto(contoDiTest);
        adminUser.setPassword(sha256("adminpassword"));
        adminUser = accountService.saveAccount(adminUser);
        accountService.flush();
        assertNotNull(adminUser.getIdAccount());
    }

    /**
     * Testa che un utente non autenticato ottenga un 403 FORBIDDEN
     */
    @Test
    public void testUnauthorizedAccess() throws Exception {
        mockMvc.perform(get("/api/account/" + normalUser1.getIdAccount()))
                .andExpect(status().isForbidden()); // 🔹 Deve restituire 403 FORBIDDEN
    }

    /**
     * Testa che un utente NON possa accedere ai dati di un altro utente.
     */
    @Test
    public void testUserCannotAccessAnotherUserAccount() throws Exception {
        mockMvc.perform(get("/api/account/" + normalUser2.getIdAccount())
                        .header("Authorization", "Bearer "))
                .andExpect(status().isForbidden()); // 🔹 Deve restituire 403 FORBIDDEN
    }

    /**
     * Testa che un utente possa accedere solo al proprio account.
     */
    @Test
    public void testUserCanAccessOwnAccount() throws Exception {
        mockMvc.perform(get("/api/account/" + normalUser1.getIdAccount())
                        .header("Authorization", "Bearer "))
                .andExpect(status().isOk());
    }

    /**
     * Testa che un admin possa accedere a qualsiasi account.
     */
    @Test
    public void testAdminCanAccessAnyAccount() throws Exception {
        mockMvc.perform(get("/api/account/" + normalUser1.getIdAccount())
                        .header("Authorization", "Bearer "))
                .andExpect(status().isOk());
    }


}
