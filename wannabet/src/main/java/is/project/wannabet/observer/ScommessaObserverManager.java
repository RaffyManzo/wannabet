package is.project.wannabet.observer;

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

    @Override
    public void update(Long idQuota) {
        List<Scommessa> scommesse = scommessaRepository.findScommesseByQuotaId(idQuota);
        scommesse.forEach(this::aggiornaStatoScommessa);
    }

    private void aggiornaStatoScommessa(Scommessa scommessa) {
        boolean almenoUnaPerdente = scommessa.getQuote().stream()
                .anyMatch(quota -> quota.getStato() == StatoQuota.PERDENTE);

        boolean tutteVincenti = scommessa.getQuote().stream()
                .allMatch(quota -> quota.getStato() == StatoQuota.VINCENTE);

        if (almenoUnaPerdente) {
            scommessa.setStato(StatoScommessa.PERSA);
        } else if (tutteVincenti) {
            scommessa.setStato(StatoScommessa.VINTA);
            contoService.aggiornaSaldoDopoVincita(scommessa);
        }

        scommessaRepository.save(scommessa);
    }


}
