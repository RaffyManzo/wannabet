package is.project.wannabet.observer;

import is.project.wannabet.controller.QuotaManager;
import is.project.wannabet.model.*;
import is.project.wannabet.repository.ScommessaRepository;
import is.project.wannabet.service.ContoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ScommessaObserverManager implements QuotaObserver {

    @Autowired
    private ScommessaRepository scommessaRepository;

    @Autowired
    private ContoService contoService;

    public ScommessaObserverManager() {
        QuotaManager.getInstance().addObserver(this);
    }

    @Override
    public void update(Long idQuota) {
        List<Scommessa> scommesse = scommessaRepository.findScommesseByQuotaId(idQuota);
        scommesse.forEach(this::aggiornaStatoScommessa);
    }

    private void aggiornaStatoScommessa(Scommessa scommessa) {
        boolean almenoUnaPerdente = scommessa.getQuoteGiocate().stream()
                .anyMatch(q -> q.getQuota().getStato() == StatoQuota.PERDENTE);

        boolean tutteVincenti = scommessa.getQuoteGiocate().stream()
                .allMatch(q -> q.getQuota().getStato() == StatoQuota.VINCENTE);

        if (almenoUnaPerdente) {
            scommessa.setStato(StatoScommessa.PERSA);
        } else if (tutteVincenti) {
            scommessa.setStato(StatoScommessa.VINTA);
            // contoService.aggiornaSaldoDopoVincita(scommessa); DISABLED FOR TESTING
        }

        scommessaRepository.save(scommessa);
    }
}
