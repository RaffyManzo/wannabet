package is.project.wannabet.test_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import is.project.wannabet.model.Evento;
import is.project.wannabet.service.EventoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Classe di test per verificare il corretto funzionamento del controller EventoController.
 * Testa tutte le operazioni CRUD e la gestione delle quote associate.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EventoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Evento eventoDiTest;

    /**
     * Configura un evento di test prima di ogni esecuzione.
     */
    @BeforeEach
    public void setup() {
        eventoDiTest = new Evento();
        eventoDiTest.setNome("Partita Test");
        eventoDiTest.setData(new Date());
        eventoDiTest.setDescrizione("Descrizione della partita di test");
        eventoDiTest.setCategoria("Calcio");
        eventoDiTest.setChiuso(false);
        eventoDiTest = eventoService.saveEvento(eventoDiTest);
    }

    @Test
    public void serializzazioneTest() {
        try {
            // Creiamo un evento di test
            Evento evento = new Evento();
            evento.setIdEvento(1L);
            evento.setNome("Finale Champions League");
            evento.setData(new Date());
            evento.setDescrizione("Partita finale della Champions");
            evento.setCategoria("Calcio");
            evento.setChiuso(false);
            evento.setQuote(List.of()); // Nessuna quota per evitare cicli di riferimento

            // Serializzazione
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(evento);

            System.out.println("Evento serializzato: " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Testa la creazione di un evento tramite il controller.
     */
    @Test
    public void testCreateEvento() throws Exception {
        Evento nuovoEvento = new Evento();
        nuovoEvento.setNome("Nuovo Evento");
        nuovoEvento.setData(new Date());
        nuovoEvento.setDescrizione("Descrizione evento");
        nuovoEvento.setCategoria("Basket");

        mockMvc.perform(post("/api/evento/create")
                        .contentType(MediaType.APPLICATION_JSON)

                        .content(objectMapper.writeValueAsString(nuovoEvento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Nuovo Evento"))
                .andExpect(jsonPath("$.categoria").value("Basket"));
    }

    /**
     * Testa il recupero di un evento esistente.
     */
    @Test
    public void testGetEventoById() throws Exception {
        mockMvc.perform(get("/api/evento/" + eventoDiTest.getIdEvento()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_evento").value(eventoDiTest.getIdEvento()))
                .andExpect(jsonPath("$.nome").value("Partita Test"));
    }

    /**
     * Testa il recupero di tutti gli eventi.
     */
    @Test
    public void testGetAllEventi() throws Exception {
        mockMvc.perform(get("/api/evento"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    /**
     * Testa la ricerca di eventi per nome.
     */
    @Test
    public void testSearchEventiByNome() throws Exception {
        mockMvc.perform(get("/api/evento/search")
                        .param("nome", "Partita"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].nome", containsString("Partita")));
    }

    /**
     * Testa la chiusura di un evento.
     */
    @Test
    public void testChiudiEvento() throws Exception {
        mockMvc.perform(patch("/api/evento/" + eventoDiTest.getIdEvento() + "/chiudi"))
                .andExpect(status().isOk())
                .andExpect(content().string("Evento chiuso, quote non giocabili."));

        // Verifica che l'evento sia ora chiuso
        Optional<Evento> eventoAggiornato = eventoService.getEventoById(eventoDiTest.getIdEvento());
        assertTrue(eventoAggiornato.isPresent());
        assertTrue(eventoAggiornato.get().isChiuso());
    }

    /**
     * Testa l'eliminazione di un evento.
     */
    @Test
    public void testDeleteEvento() throws Exception {
        mockMvc.perform(delete("/api/evento/" + eventoDiTest.getIdEvento()))
                .andExpect(status().isOk());

        // Verifica che l'evento sia stato eliminato
        Optional<Evento> eventoEliminato = eventoService.getEventoById(eventoDiTest.getIdEvento());
        assertFalse(eventoEliminato.isPresent());
    }

    /**
     * Testa la modifica di un evento.
     */
    @Test
    public void testUpdateEvento() throws Exception {
        eventoDiTest.setNome("Partita Modificata");

        mockMvc.perform(put("/api/evento/" + eventoDiTest.getIdEvento())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventoDiTest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Partita Modificata"));
    }
}
