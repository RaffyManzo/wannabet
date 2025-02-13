package is.project.wannabet.test_security;

import com.fasterxml.jackson.databind.ObjectMapper;
import is.project.wannabet.model.Evento;
import is.project.wannabet.model.Quota;
import is.project.wannabet.model.StatoQuota;
import is.project.wannabet.model.TipoAccount;
import is.project.wannabet.service.EventoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {


    @Autowired
    private EventoService eventoService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 1️⃣ Un utente tenta di accedere a un'area riservata a `GESTORE_EVENTO` o `ADMIN`
     */
    @Test
    @WithMockUser(username = "utente@email.com", roles = {"UTENTE"})
    public void testUtenteAccedeAdAreaGestoreOAdmin() throws Exception {
        mockMvc.perform(get("/api/quota/list"))
                .andExpect(status().isForbidden());

        Evento eventoDiTest = eventoService.createEvento("Sinner vs Medvedev", new Date(), "", "Tennis");


        Quota nuovaQuota = new Quota();
        nuovaQuota.setMoltiplicatore(2.5);
        nuovaQuota.setEsito("1");
        nuovaQuota.setChiusa(false);
        nuovaQuota.setCategoria("Risultato Finale");
        nuovaQuota.setEvento(eventoDiTest);
        nuovaQuota.setStato(StatoQuota.DA_REFERTARE);

        System.out.println("JSON inviato: " + objectMapper.writeValueAsString(nuovaQuota));



        mockMvc.perform(post("/api/quota/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuovaQuota)))
                .andExpect(status().isForbidden());
    }

    /**
     * 2️⃣ Qualcuno tenta di accedere a un'area riservata senza autenticazione
     */
    @Test
    public void testAccessoAreaRiservataSenzaLogin() throws Exception {
        mockMvc.perform(get("/api/conto/1/preleva"))
                .andExpect(status().isUnauthorized()); // Deve restituire 401 UNAUTHORIZED
    }

    /**
     * 3️⃣ Un utente tenta di accedere all'area di un altro utente
     */
    @Test
    @WithMockUser(username = "utente1@email.com", roles = {"UTENTE"})
    public void testUtenteAccedeAdAreaDiAltroUtente() throws Exception {
        mockMvc.perform(get("/api/conto/1/preleva")) // ID diverso dall'utente autenticato
                .andExpect(status().isForbidden()); // Deve restituire 403 FORBIDDEN
    }

    /**
     * 4️⃣ Un utente accede alla propria area riservata con successo
     */
    @Test
    @WithMockUser(username = "testSC@email.com", roles = {"UTENTE"})
    public void testUtenteAccedeAllaPropriaAreaConSuccesso() throws Exception {
        mockMvc.perform(get("/api/conto/1/preleva")) // ID corrisponde all'utente autenticato
                .andExpect(status().isOk()); // Deve restituire 200 OK
    }

    /**
     * 5️⃣ Admin e gestori accedono alle loro aree riservate con successo
     */
    @Test
    @WithMockUser(username = "admin@email.com", roles = {"ADMIN"})
    public void testAdminAccedeAllaPropriaArea() throws Exception {
        mockMvc.perform(get("/api/quota/list"))
                .andExpect(status().isOk()); // Deve restituire 200 OK
    }

    @Test
    @WithMockUser(username = "gestore@email.com", roles = {"GESTORE_EVENTO"})
    public void testGestoreAccedeAllaPropriaArea() throws Exception {
        mockMvc.perform(get("/api/quota/list"))
                .andExpect(status().isOk()); // Deve restituire 200 OK
    }
}
