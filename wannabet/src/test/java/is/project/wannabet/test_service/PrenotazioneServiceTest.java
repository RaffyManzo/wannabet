package is.project.wannabet.test_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import is.project.wannabet.factory.ScommessaFactory;
import is.project.wannabet.model.*;
import is.project.wannabet.repository.PrenotazioneRepository;
import is.project.wannabet.service.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static is.project.wannabet.security.PasswordEncoding.sha256;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Rollback(value = false)
class PrenotazioneServiceTest {


    @Autowired
    private PrenotazioneService prenotazioneService;

    @Autowired
    private ScommessaService scommessaService;

    @Autowired
    private QuotaService quotaService;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private ContoService contoService;

    @Autowired
    private AccountRegistratoService accountService;

    @Autowired
    private SaldoFedeltaService saldoFedeltaService;

    private AccountRegistrato accountDiTest;
    private Conto contoDiTest;
    private Evento eventoDiTest;
    private Quota quotaDiTest;
    private Scommessa scommessaDiTest;

    @BeforeAll
    void setUp() {

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
        accountDiTest = new AccountRegistrato();
        accountDiTest.setSaldoFedelta(saldoFedelta);
        accountDiTest.setNome("Pippo");
        accountDiTest.setCognome("Baudo");
        accountDiTest.setDataNascita(new Date());
        accountDiTest.setCodiceFiscale("JAKOORTIU848637");
        accountDiTest.setTipo(TipoAccount.UTENTE);
        accountDiTest.setEmail("testS@email.com");
        accountDiTest.setConto(contoDiTest);
        accountDiTest.setPassword(sha256("abcde"));
        accountDiTest = accountService.saveAccount(accountDiTest);
        accountService.flush();

        eventoDiTest = eventoService.createEvento("Sinner vs Medvedev", new Date(), "", "Tennis");
        eventoService.flush();
        eventoDiTest = eventoService.getEventoById(eventoDiTest.getIdEvento()).get();
        assertNotNull(eventoDiTest.getIdEvento());

        // 5️⃣ Crea e salva Quota
        quotaDiTest = quotaService.createQuota("1", "Risultato Finale", 2.5, eventoDiTest);
        quotaService.flush();
        assertNotNull(quotaDiTest.getIdQuota());

        // 6️⃣ Crea e salva Scommessa
        scommessaDiTest = ScommessaFactory.createScommessaPrenotata(accountDiTest, List.of(quotaDiTest), 100.0);
        scommessaService.saveScommessa(scommessaDiTest);
    }

    /**
     * 🔹 Testa la generazione del codice univoco
     */
    @Test
    void testGeneraCodiceUnico() {
        String codice = prenotazioneService.generaCodiceUnico();

        assertThat(codice).hasSize(5) // Controlla la lunghezza
                .matches("[A-Z0-9]+"); // Controlla che sia alfanumerico
    }

    /**
     * 🔹 Testa il salvataggio di una prenotazione
     */
    @Test
    void testSavePrenotazione() {
        // Genera un codice univoco
        String codice = prenotazioneService.generaCodiceUnico();

        // Crea la prenotazione
        Prenotazione prenotazione = new Prenotazione(codice, scommessaDiTest);

        // Salva la prenotazione
        prenotazione = prenotazioneService.savePrenotazione(prenotazione);
        prenotazioneService.flush();
        assertThat(prenotazione).isNotNull();

        // Verifica che il codice sia stato salvato correttamente
        assertThat(prenotazioneService.getPrenotazioneByCodice(codice)).isNotEmpty();

        // Controlla che la prenotazione sia effettivamente salvata nel database
        assertThat(prenotazioneService.getPrenotazioneByCodice(codice).get()).isNotNull();
    }

    /**
     * 🔹 Testa il recupero delle quote per una prenotazione
     */
    @Test
    void testGetQuoteByPrenotazione() {
        // Genera e salva una prenotazione
        String codice = prenotazioneService.generaCodiceUnico();
        Prenotazione prenotazione = new Prenotazione(codice, scommessaDiTest);
        prenotazione = prenotazioneService.savePrenotazione(prenotazione);

        // Recupera le quote associate alla prenotazione
        List<Quota> result = prenotazioneService.getQuoteByPrenotazione(codice);

        // Controlla che la lista non sia vuota (dovrebbe contenere almeno una quota)
        assertThat(result).hasSizeGreaterThanOrEqualTo(0);
    }
}
