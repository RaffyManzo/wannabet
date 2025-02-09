package is.project.wannabet.test_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import is.project.wannabet.WannabetApplication;
import is.project.wannabet.controller.QuotaManager;
import is.project.wannabet.controller.ScommessaController;
import is.project.wannabet.factory.EventoFactory;
import is.project.wannabet.factory.QuotaFactory;
import is.project.wannabet.factory.ScommessaFactory;
import is.project.wannabet.model.*;
import is.project.wannabet.repository.*;
import is.project.wannabet.service.ScommessaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Classe di test per `ScommessaController`.
 * Verifica che la creazione e l'aggiornamento delle scommesse funzioni correttamente.
 */
@SpringBootTest(classes = WannabetApplication.class) // Indica la classe principale
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WithMockUser(username = "testuser", roles = {"USER"})
public class ScommessaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ScommessaService scommessaService;

    @Mock
    private ScommessaRepository scommessaRepository;

    @Test
    public void testGetScommessaById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/scommesse/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



    /**
     * Test per verificare la creazione di una nuova scommessa.
     */

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private QuotaRepository quotaRepository;

    @Autowired
    private ContoRepository contoRepository;

    @Autowired
    private AccountRegistratoRepository accountRegistratoRepository;

    @Autowired
    private SaldoFedeltaRepository saldoFedeltaRepository;

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    public void testCreateScommessa() throws Exception {
        // Creazione dell'evento
        Evento evento = eventoRepository.save(EventoFactory.createEvento("evento1", new Date(), "des", "Calcio"));

        // Creazione della quota legata all'evento
        Quota q = QuotaFactory.createQuota(evento, 1.70, "1", "Risultato finale");
        q = quotaRepository.save(q);  // Salvataggio per generare l'ID

        // Creazione del conto e salvataggio nel database
        Conto conto = new Conto();
        conto.setSaldo(1000.00);
        conto.setDataCreazione(new Date());
        conto.setIndirizzoFatturazione("Via Roma, 10");
        conto = contoRepository.save(conto);

        SaldoFedelta saldoFedelta = new SaldoFedelta();
        saldoFedelta.setPunti(10);
        saldoFedelta = saldoFedeltaRepository.save(saldoFedelta);

        // Creazione di un account senza impostare manualmente l'ID
        AccountRegistrato ac = new AccountRegistrato();
        ac.setCodiceFiscale("XYZ12345");  // Devi settare campi validi
        ac.setNome("Mario");
        ac.setCognome("Rossi");
        ac.setSaldoFedelta(saldoFedelta);
        ac.setConto(conto);
        ac.setTipo(TipoAccount.UTENTE);
        ac.setEmail("mario.rossi@example.com");
        ac = accountRegistratoRepository.save(ac);  // Hibernate assegnerà l'ID

        // Creazione della scommessa
        Scommessa nuovaScommessa = ScommessaFactory.createScommessa(
                ac, List.of(q), 100
        );

        mockMvc.perform(post("/api/scommesse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuovaScommessa)))
                .andExpect(status().isOk());
    }



}
