package is.project.wannabet.service;

import is.project.wannabet.model.Prenotazione;
import is.project.wannabet.model.Quota;
import is.project.wannabet.repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    private static final String CARATTERI = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LUNGHEZZA_CODICE = 5;
    private final SecureRandom random = new SecureRandom();

    /**
     * Ottiene la lista di quote associate a una prenotazione dato il codice.
     *
     * @param codice Codice della prenotazione.
     * @return Lista di quote associate alla scommessa della prenotazione.
     */
    public List<Quota> getQuoteByPrenotazione(String codice) {
        return prenotazioneRepository.findQuoteByCodicePrenotazione(codice);
    }

    @Transactional
    public Prenotazione savePrenotazione(Prenotazione prenotazione) {

        return prenotazioneRepository.save(prenotazione);
    }


    public Optional<Prenotazione> getPrenotazioneByCodice(String codice) {
        return prenotazioneRepository.findByCodice(codice);
    }

    /**
     * Genera un codice univoco di 5 caratteri alfanumerici che non esiste già nel database.
     *
     * @return Codice univoco per la prenotazione.
     */
    public String generaCodiceUnico() {
        String codice;
        do {
            codice = generaCodiceCasuale();
        } while (prenotazioneRepository.existsByCodice(codice)); // Controlla se il codice è già usato

        return codice;
    }

    @Transactional
    public void flush() {
        prenotazioneRepository.flush();
    }

    /**
     * Genera un codice alfanumerico casuale di 5 caratteri.
     *
     * @return Codice casuale.
     */
    private String generaCodiceCasuale() {
        StringBuilder sb = new StringBuilder(LUNGHEZZA_CODICE);
        for (int i = 0; i < LUNGHEZZA_CODICE; i++) {
            sb.append(CARATTERI.charAt(random.nextInt(CARATTERI.length())));
        }
        return sb.toString();
    }

}
