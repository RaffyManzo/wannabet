package is.project.wannabet.service;

import is.project.wannabet.factory.ScommessaFactory;
import is.project.wannabet.model.*;
import is.project.wannabet.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ScommessaService {

    @Autowired
    private ScommessaRepository scommessaRepository;

    @Autowired
    private QuotaRepository quotaRepository;

    public List<Scommessa> getAllScommesse() {
        return scommessaRepository.findAll();
    }

    public Optional<Scommessa> getScommessaById(Long id) {
        return scommessaRepository.findById(id);
    }

    public Scommessa saveScommessa(Scommessa scommessa) {
        // Verifichiamo che tutte le quote esistano
        List<QuotaGiocata> quoteGiocate = scommessa.getQuoteGiocate().stream()
                .map(qg -> new QuotaGiocata(scommessa, quotaRepository.findById(qg.getQuota().getIdQuota())
                        .orElseThrow(() -> new IllegalArgumentException("Quota non trovata con ID: " + qg.getQuota().getIdQuota()))))
                .toList();

        // Assegniamo le quote giocate alla scommessa
        scommessa.setQuoteGiocate(quoteGiocate);

        // Salviamo la scommessa con le quote giocate
        return scommessaRepository.save(scommessa);
    }


    @Transactional
    public void creaScommessaDaScontrino(List<Quota> quote, double importo) {
        if (quote == null || quote.isEmpty()) {
            throw new IllegalArgumentException("Una scommessa deve contenere almeno una quota.");
        }
        if (importo <= 0) {
            throw new IllegalArgumentException("L'importo della scommessa deve essere positivo.");
        }

        // Supponiamo che l'account venga identificato in sessione (da implementare con Spring Security)
        AccountRegistrato account = getAccountCorrente(); // TODO: Recuperare l'account dell'utente loggato

        Scommessa scommessa = ScommessaFactory.createScommessa(account, quote, importo);
        scommessaRepository.save(scommessa);
    }

    // Da rimuovre qui solo per testing

    @Autowired
    private ContoRepository contoRepository;

    @Autowired
    private AccountRegistratoRepository accountRegistratoRepository;

    @Autowired
    private SaldoFedeltaRepository saldoFedeltaRepository;


    private AccountRegistrato getAccountCorrente() {

        // Creazione del conto e salvataggio nel database
        Conto conto = new Conto();
        conto.setSaldo(500.00);
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
        ac = accountRegistratoRepository.save(ac);


        // TODO: Recuperare l'account dalla sessione o dal contesto di Spring Security
        return ac; // Placeholder temporaneo
    }
}
