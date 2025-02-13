package is.project.wannabet.test_auth;

import is.project.wannabet.model.*;
import is.project.wannabet.service.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Date;

import static is.project.wannabet.security.PasswordEncoding.sha256;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test di sicurezza per le API protette
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ContoService contoService;

    @Autowired
    private AccountRegistratoService accountService;

    @Autowired
    private SaldoFedeltaService saldoFedeltaService;

    private AccountRegistrato accountDiTest1;
    private AccountRegistrato accountDiTest2;
    private Conto contoDiTest;


    @BeforeAll
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
        accountDiTest1 = new AccountRegistrato();
        accountDiTest1.setSaldoFedelta(saldoFedelta);
        accountDiTest1.setNome("Pippo");
        accountDiTest1.setCognome("Baudo");
        accountDiTest1.setDataNascita(new Date());
        accountDiTest1.setCodiceFiscale("JAKOORGIU868937");
        accountDiTest1.setTipo(TipoAccount.UTENTE);
        accountDiTest1.setEmail("test@email.com");
        accountDiTest1.setConto(contoDiTest);
        accountDiTest1.setPassword(sha256("abcde"));
        accountDiTest1 = accountService.saveAccount(accountDiTest1);
        accountService.flush();
        assertNotNull(accountDiTest1.getIdAccount());

        accountDiTest2 = new AccountRegistrato();
        accountDiTest2.setSaldoFedelta(saldoFedelta);
        accountDiTest2.setNome("Pippo");
        accountDiTest2.setCognome("Baudo");
        accountDiTest2.setDataNascita(new Date());
        accountDiTest2.setCodiceFiscale("JAKOORGIU848937");
        accountDiTest2.setTipo(TipoAccount.UTENTE);
        accountDiTest2.setEmail("test2@email.com");
        accountDiTest2.setConto(contoDiTest);
        accountDiTest2.setPassword(sha256("abcde"));
        accountDiTest2 = accountService.saveAccount(accountDiTest2);
        accountService.flush();
        assertNotNull(accountDiTest2.getIdAccount());
    }

    /**
     * 1️⃣ Un utente tenta di accedere a un'area gestore o admin → Deve fallire (403)
     */
    @Test
    @WithMockUser(username = "test@email.com", roles = {"UTENTE"})
    public void testUtenteAccedeAdAreaGestoreOAdmin() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("DEBUG - Utente autenticato: " + auth.getName());
        System.out.println("DEBUG - Ruoli: " + auth.getAuthorities());

        mockMvc.perform(get("/api/quota/list"))
                .andExpect(status().isForbidden()); // 403 FORBIDDEN

        Evento eventoDiTest = eventoService.createEvento("Sinner vs Medvedev", new Date(), "", "Tennis");
        assertNotNull(eventoDiTest);

        Quota nuovaQuota = new Quota();
        nuovaQuota.setMoltiplicatore(2.5);
        nuovaQuota.setEsito("1");
        nuovaQuota.setChiusa(false);
        nuovaQuota.setCategoria("Risultato Finale");
        nuovaQuota.setEvento(eventoDiTest);
        nuovaQuota.setStato(StatoQuota.DA_REFERTARE);

        System.out.println("DEBUG - JSON inviato: " + objectMapper.writeValueAsString(nuovaQuota));

        mockMvc.perform(post("/api/quota/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuovaQuota)))
                .andExpect(status().isForbidden()); // 403 FORBIDDEN
    }

    /**
     * 2️⃣ Accesso a un'area riservata senza autenticazione → Deve fallire (401)
     */
    @Test
    public void testAccessoAreaRiservataSenzaLogin() throws Exception {
        mockMvc.perform(post("/api/conto/"+accountDiTest1.getIdAccount()+"/preleva").param("importo", "100"))
                .andExpect(status().isUnauthorized()); // 401 UNAUTHORIZED
    }

    /**
     * 3️⃣ Un utente tenta di accedere all'area di un altro utente → Deve fallire (403)
     */
    @Test
    @WithUserDetails(value = "test@email.com", userDetailsServiceBeanName = "accountDetailsService")
    public void testUtenteAccedeAdAreaDiAltroUtente() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("DEBUG - Utente autenticato: " + auth.getName());

        mockMvc.perform(post("/api/conto/"+accountDiTest2.getIdAccount()+"/preleva").param("importo", "100")) // ID diverso dall'utente autenticato
                .andExpect(status().isForbidden()); // 403 FORBIDDEN
    }

    /**
     * 4️⃣ Un utente accede alla propria area riservata con successo → Deve passare (200)
     */
    @Test
    @WithUserDetails(value = "test@email.com", userDetailsServiceBeanName = "accountDetailsService")
    public void testUtenteAccedeAllaPropriaAreaConSuccesso() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("DEBUG - Utente autenticato: " + auth.getName());

        mockMvc.perform(post("/api/conto/"+accountDiTest1.getIdAccount()+"/preleva").param("importo", "100")) // ID corrisponde all'utente autenticato
                .andExpect(status().isOk()); // 200 OK
    }

    /**
     * 5️⃣ Admin accede alla propria area riservata → Deve passare (200)
     */
    @Test
    @WithMockUser(username = "admin@email.com", roles = {"ADMIN"})
    public void testAdminAccedeAllaPropriaArea() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("DEBUG - Admin autenticato: " + auth.getName());

        mockMvc.perform(get("/api/quota/list"))
                .andExpect(status().isOk()); // 200 OK
    }

    /**
     * 6️⃣ Gestore accede alla propria area riservata → Deve passare (200)
     */
    @Test
    @WithMockUser(username = "gestore@email.com", roles = {"GESTORE_EVENTO"})
    public void testGestoreAccedeAllaPropriaArea() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("DEBUG - Gestore autenticato: " + auth.getName());

        mockMvc.perform(get("/api/quota/list"))
                .andExpect(status().isOk()); // 200 OK
    }
}
