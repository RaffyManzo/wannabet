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

    // Valori validi di base
    private final String validEmail = "valid@example.com";
    private final String validPassword = "password123";
    private final String validNome = "Luca";
    private final String validCognome = "Bianchi";
    private final String validCodiceFiscale = "RSSMRA85M01H501U";
    private final String validIndirizzo = "Via Roma 10";
    private final Date validData = new Date();

    @BeforeAll
    void setUp() {

        Conto newConto = ContoFactory.createConto("Via abcde");
        contoService.saveConto(newConto);

        SaldoFedelta saldoFedelta = SaldoFedeltaFactory.createSaldoFedelta();
        saldoFedeltaService.saveSaldoFedelta(saldoFedelta);

        // Creazione di un account di test nel database

        AccountRegistrato account = AccountRegistratoFactory.createAccountRegistrato(
                "Mario", "Rossi", "ABCDEK12G34H567I", newConto, new Date(), "alibabu@email.com",
                passwordEncoder.encode("password123"), saldoFedelta
        );
        accountRegistratoService.saveAccount(account);
    }

    // =======================================================
    // TEST DI SUCCESSO
    // =======================================================


    /**
     * Testa il login con credenziali corrette.
     */
    @Test
    void testLoginSuccesso() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("alibabu@email.com");
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


    @Test
    void testRegistrazioneEmailNonValida() throws Exception {
        RegistrazioneRequest request = createValidRequest();
        request.setEmail("notanemail");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    // --- Test per Password ---
    @Test
    void testRegistrazionePasswordTroppoCorta() throws Exception {
        RegistrazioneRequest request = createValidRequest();
        request.setPassword("123"); // Meno di 6 caratteri

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegistrazionePasswordTroppoLunga() throws Exception {
        RegistrazioneRequest request = createValidRequest();
        // Genera una password di 65 caratteri
        request.setPassword("12345678901234567890123456789012345678901234567890123456789012345");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    // --- Test per Nome ---
    @Test
    void testRegistrazioneNomeConCaratteriNonAmmessi() throws Exception {
        RegistrazioneRequest request = createValidRequest();
        request.setNome("Luca123!@#"); // Contiene numeri e caratteri speciali

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    // --- Test per Cognome ---
    @Test
    void testRegistrazioneCognomeConCaratteriNonAmmessi() throws Exception {
        RegistrazioneRequest request = createValidRequest();
        request.setCognome("Bianchi99"); // Contiene numeri

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    // --- Test per Codice Fiscale ---
    @Test
    void testRegistrazioneCodiceFiscaleFormatoErrato() throws Exception {
        RegistrazioneRequest request = createValidRequest();
        request.setCodiceFiscale("INVALIDCODE"); // Non rispetta il pattern

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegistrazioneCodiceFiscaleTroppoCorto() throws Exception {
        RegistrazioneRequest request = createValidRequest();
        request.setCodiceFiscale("SHORT123"); // Troppo corto

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegistrazioneCodiceFiscaleTroppoLungo() throws Exception {
        RegistrazioneRequest request = createValidRequest();
        request.setCodiceFiscale("ABCDEFGHIJKLMNOXYZ"); // Troppo lungo

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    // --- Test per Indirizzo di Fatturazione ---
    @Test
    void testRegistrazioneIndirizzoSenzaNumero() throws Exception {
        RegistrazioneRequest request = createValidRequest();
        request.setIndirizzoDiFatturazione("Via Roma"); // Mancanza di un numero

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegistrazioneIndirizzoTroppoLungo() throws Exception {
        RegistrazioneRequest request = createValidRequest();
        // Genera un indirizzo troppo lungo
        request.setIndirizzoDiFatturazione("Via ".repeat(30));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    // --- Test per Data di Nascita ---
    @Test
    void testRegistrazioneDataDiNascitaNull() throws Exception {
        RegistrazioneRequest request = createValidRequest();
        request.setDataDiNascita(null); // Data nulla

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    // Metodo di utilità per creare una request valida di base
    private RegistrazioneRequest createValidRequest() {
        RegistrazioneRequest request = new RegistrazioneRequest();
        request.setEmail(validEmail);
        request.setPassword(validPassword);
        request.setNome(validNome);
        request.setCognome(validCognome);
        request.setCodiceFiscale(validCodiceFiscale);
        request.setIndirizzoDiFatturazione(validIndirizzo);
        request.setDataDiNascita(validData);
        return request;
    }
}
