package is.project.wannabet.test_controller;

import is.project.wannabet.controller.QuotaController;
import is.project.wannabet.factory.EventoFactory;
import is.project.wannabet.factory.QuotaFactory;
import is.project.wannabet.model.Evento;
import is.project.wannabet.model.Quota;
import is.project.wannabet.model.StatoQuota;
import is.project.wannabet.repository.QuotaRepository;
import is.project.wannabet.service.QuotaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Classe di test per QuotaController.
 */
@ExtendWith(MockitoExtension.class)
public class QuotaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private QuotaService quotaService;

    @InjectMocks
    private QuotaController quotaController;

    /**
     * Test per il recupero di una quota per ID.
     */
    @Test
    public void testGetQuotaById() throws Exception {
        // Inizializza MockMvc con il controller
        mockMvc = MockMvcBuilders.standaloneSetup(quotaController).build();

        // Creazione quota di test
        Quota quota = QuotaFactory.createQuota(
                EventoFactory.createEvento("evento1", new Date(), "des", "Calcio"),
                1.70,
                "1",
                "Risultato finale"
        );

        // Simulazione della chiamata al service
        when(quotaService.getQuotaById(1L)).thenReturn(Optional.of(quota));

        // Esegui la richiesta e verifica il risultato
        mockMvc.perform(MockMvcRequestBuilders.get("/api/quota/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.esito", is("1")))
                .andExpect(jsonPath("$.stato", is(StatoQuota.DA_REFERTARE.toString())));
    }


    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    public void testRefertaQuota() throws Exception {
        // Inizializza MockMvc con il controller
        mockMvc = MockMvcBuilders.standaloneSetup(quotaController).build();


        // Test 1: Referto corretto -> la quota diventa Vincente
        mockMvc.perform(MockMvcRequestBuilders.post("/api/quota/referta/1")
                        .param("referto", "1") // Referto errato
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        // Test 2: Referto errato -> la quota diventa PERDENTE
        mockMvc.perform(MockMvcRequestBuilders.post("/api/quota/referta/1")
                        .param("referto", "2") // Referto errato
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Test 3: Quota non trovata -> dovrebbe restituire 404 NOT FOUND
        when(quotaService.getQuotaById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/quota/referta/2")
                        .param("referto", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // Test 4: Referto nullo o vuoto -> dovrebbe restituire 400 BAD REQUEST
        mockMvc.perform(MockMvcRequestBuilders.post("/api/quota/referta/1")
                        .param("referto", "") // Referto vuoto
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/quota/referta/1")
                        .contentType(MediaType.APPLICATION_JSON)) // Nessun referto inviato
                .andExpect(status().isBadRequest());
    }
}
