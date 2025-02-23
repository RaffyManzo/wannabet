package is.project.wannabet.thymeleafcontroller_test;

import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.model.Scommessa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ListaScommesseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestTemplate restTemplate; // Ora viene iniettato

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setup() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    @WithMockUser(username = "testS@email.com",
            roles = {"UTENTE"})
    public void testListaScommessePage() throws Exception {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        String accountJson = "{\"idAccount\": 1, \"email\": \"testS@email.com\"}";
        mockServer.expect(ExpectedCount.once(),
                        requestTo(baseUrl + "/api/account/" + "testS@email.com"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(accountJson, MediaType.APPLICATION_JSON));

        String scommesseJson = "[{\"idScommessa\": 101, \"stato\": \"Da refertare\", \"data\": \"2025-01-01T12:00:00\", \"importo\": 4, \"vincita\": 100.00}, {\"idScommessa\": 102, \"stato\": \"Persa\", \"data\": \"2025-01-02T15:30:00\", \"importo\": 4, \"vincita\": 0}]";
        mockServer.expect(ExpectedCount.once(),
                        requestTo(baseUrl + "/api/scommesse/account/12"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(scommesseJson, MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/lista-scommesse"))
                .andExpect(status().isOk())
                .andExpect(view().name("lista-scommesse"))
                .andExpect(model().attributeExists("lista-scommesse"));

        mockServer.verify();
    }
}
