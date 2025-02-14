package is.project.wannabet.test_auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import is.project.wannabet.factory.AccountRegistratoFactory;
import is.project.wannabet.factory.ContoFactory;
import is.project.wannabet.factory.SaldoFedeltaFactory;
import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.model.Conto;
import is.project.wannabet.model.SaldoFedelta;
import is.project.wannabet.model.TipoAccount;
import is.project.wannabet.payload.LoginRequest;
import is.project.wannabet.payload.RegistrazioneRequest;
import is.project.wannabet.service.AccountRegistratoService;
import is.project.wannabet.service.ContoService;
import is.project.wannabet.service.SaldoFedeltaService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRegistratoService accountRegistratoService;

    @Autowired
    private ContoService contoService;

    @Autowired
    private SaldoFedeltaService saldoFedeltaService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    void setUp() {

        Conto newConto = ContoFactory.createConto("Via abcde");
        contoService.saveConto(newConto);

        SaldoFedelta saldoFedelta = SaldoFedeltaFactory.createSaldoFedelta();
        saldoFedeltaService.saveSaldoFedelta(saldoFedelta);

        // Creazione di un account di test nel database

        AccountRegistrato account = AccountRegistratoFactory.createAccountRegistrato(
                "Mario", "Rossi", "ABCDEF12G34H567I", newConto, new Date(), "alibaba@email.com",
                passwordEncoder.encode("password123"), saldoFedelta
        );
        accountRegistratoService.saveAccount(account);
    }

    // =======================================================
    // TEST DI SUCCESSO
    // =======================================================

    /**
     * Testa la registrazione con tutti i campi corretti.
     */
    @Test
    void testRegistrazioneSuccesso() throws Exception {
        RegistrazioneRequest request = new RegistrazioneRequest();
        request.setEmail("nuovo@email.com");
        request.setNome("Luca");
        request.setCognome("Bianchi");
        request.setCodiceFiscale("ABCD1234ABCD2345");
        request.setDataDiNascita(new Date());
        request.setIndirizzoDiFatturazione("via");
        request.setPassword(passwordEncoder.encode("password123"));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Registrazione effettuata con successo"));
    }

    /**
     * Testa il login con credenziali corrette.
     */
    @Test
    void testLoginSuccesso() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("alibaba@email.com");
        request.setPassword("password123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Login effettuato con successo"));
    }

    /**
     * Testa il logout.
     */
    @Test
    void testLogoutSuccesso() throws Exception {
        mockMvc.perform(post("/api/auth/logout"))
                .andExpect(status().isOk());
    }

    // =======================================================
    // TEST DI FALLIMENTO
    // =======================================================

    /**
     * Testa la registrazione senza email (deve fallire).
     */
    @Test
    void testRegistrazioneSenzaEmail() throws Exception {
        RegistrazioneRequest request = new RegistrazioneRequest();
        request.setNome("Luca");
        request.setCognome("Bianchi");
        request.setCodiceFiscale("XYZABC12G34H567I");
        request.setDataDiNascita(new Date());

        request.setIndirizzoDiFatturazione("via");
        request.setPassword(passwordEncoder.encode("password123"));


        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()); // 400 BAD REQUEST
    }

    /**
     * Testa il login con password errata (deve fallire).
     */
    @Test
    void testLoginPasswordErrata() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@email.com");
        request.setPassword("wrongpassword");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized()); // 401 UNAUTHORIZED
    }

    /**
     * Testa la registrazione con codice fiscale errato (deve fallire).
     */
    @Test
    void testRegistrazioneCodiceFiscaleErrato() throws Exception {
        RegistrazioneRequest request = new RegistrazioneRequest();
        request.setEmail("nuovo@email.com");
        request.setNome("Luca");
        request.setCognome("Bianchi");
        request.setCodiceFiscale("XYZABC"); // Codice fiscale troppo corto
        request.setDataDiNascita(new Date());
        request.setPassword(passwordEncoder.encode("password123"));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()); // 400 BAD REQUEST
    }
}
