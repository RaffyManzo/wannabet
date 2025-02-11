package is.project.wannabet.test_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import is.project.wannabet.model.Conto;
import is.project.wannabet.service.ContoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ContoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContoService contoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Conto contoDiTest;

    @BeforeEach
    public void setup() {
        contoDiTest = new Conto();
        contoDiTest.setDataCreazione(new Date());
        contoDiTest.setIndirizzoFatturazione("Via .");
        contoDiTest.setSaldo(100.0);
        contoDiTest = contoService.saveConto(contoDiTest);
    }

    @Test
    public void testVerificaSaldoSufficiente() throws Exception {
        mockMvc.perform(get("/api/conto/" + contoDiTest.getIdConto() + "/verifica-saldo")
                        .param("importo", "50"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testVerificaSaldoInsufficiente() throws Exception {
        mockMvc.perform(get("/api/conto/" + contoDiTest.getIdConto() + "/verifica-saldo")
                        .param("importo", "200"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    public void testDeposita() throws Exception {
        mockMvc.perform(post("/api/conto/" + contoDiTest.getIdConto() + "/deposita")
                        .param("importo", "50"))
                .andExpect(status().isOk());

        assertTrue(contoService.verificaSaldo(contoDiTest.getIdConto(), 150.0));
    }

    @Test
    public void testPreleva() throws Exception {
        mockMvc.perform(post("/api/conto/" + contoDiTest.getIdConto() + "/preleva")
                        .param("importo", "50"))
                .andExpect(status().isOk());

        assertFalse(contoService.verificaSaldo(contoDiTest.getIdConto(), 100.0));
    }

    @Test
    public void testPrelievoInsufficiente() throws Exception {
        mockMvc.perform(post("/api/conto/" + contoDiTest.getIdConto() + "/preleva")
                        .param("importo", "200"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCRUDConto() throws Exception {
        Conto nuovoConto = new Conto();
        nuovoConto.setSaldo(500.0);

        String json = objectMapper.writeValueAsString(nuovoConto);

        // Creazione
        mockMvc.perform(post("/api/conto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.saldo").value(500.0));
    }
}
