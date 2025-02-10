package is.project.wannabet.service;

import is.project.wannabet.exception.SaldoInsufficienteException;
import is.project.wannabet.model.Conto;
import is.project.wannabet.model.Scommessa;
import is.project.wannabet.repository.ContoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servizio per la gestione dei conti degli utenti nel sistema di scommesse.
 * Gestisce operazioni come prelievi, depositi e aggiornamenti di saldo.
 */
@Service
public class ContoService {

    @Autowired
    private ContoRepository contoRepository;

    /**
     * Recupera tutti i conti presenti nel sistema.
     *
     * @return Lista di tutti i conti disponibili.
     */
    public List<Conto> getAllConti() {
        return contoRepository.findAll();
    }

    /**
     * Recupera un conto specifico tramite il suo ID.
     *
     * @param id ID del conto da recuperare.
     * @return Optional contenente il conto, se presente.
     */
    public Optional<Conto> getContoById(Long id) {
        return contoRepository.findById(id);
    }

    /**
     * Salva un nuovo conto o aggiorna un conto esistente nel database.
     *
     * @param conto Oggetto conto da salvare.
     * @return Conto salvato.
     */
    public Conto saveConto(Conto conto) {
        return contoRepository.save(conto);
    }

    /**
     * Elimina un conto in base al suo ID.
     *
     * @param id ID del conto da eliminare.
     */
    public void deleteConto(Long id) {
        contoRepository.deleteById(id);
    }

    /**
     * Verifica se il conto associato a un dato ID ha saldo sufficiente per un'operazione.
     *
     * @param accountId ID dell'account associato al conto.
     * @param importo   Importo da verificare.
     * @return true se il saldo è sufficiente, false altrimenti.
     */
    public boolean verificaSaldo(Long accountId, double importo) {
        Conto conto = getContoById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Conto non trovato"));

        return conto.verificaSaldo(importo);
    }

    /**
     * Effettua un prelievo dal conto di un utente, aggiornando il saldo.
     * L'operazione è transazionale per garantire consistenza.
     *
     * @param accountId ID dell'account associato al conto.
     * @param importo   Importo da prelevare.
     * @throws SaldoInsufficienteException se il saldo è insufficiente.
     */
    @Transactional
    public void preleva(Long accountId, double importo) {
        Conto conto = getContoById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Conto non trovato"));

        if (conto.getSaldo() < importo) {
            throw new SaldoInsufficienteException("Saldo insufficiente per prelevare " + importo);
        }

        conto.preleva(importo);
        contoRepository.save(conto);
    }

    /**
     * Effettua un deposito su un conto, incrementando il saldo.
     * L'operazione è transazionale per garantire consistenza.
     *
     * @param accountId ID dell'account associato al conto.
     * @param importo   Importo da depositare.
     */
    @Transactional
    public void deposita(Long accountId, double importo) {
        Conto conto = getContoById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Conto non trovato"));

        conto.deposita(importo);
        contoRepository.save(conto);
    }

    /**
     * Aggiorna il saldo di un conto dopo la vincita di una scommessa.
     * Se la scommessa è stata vinta, il saldo del conto viene incrementato dell'importo vinto.
     *
     * @param scommessa Scommessa da cui recuperare l'importo della vincita.
     * @throws IllegalArgumentException se il conto associato alla scommessa non viene trovato.
     */
    @Transactional
    public void aggiornaSaldoDopoVincita(Scommessa scommessa) {
        if (scommessa.getStato() == is.project.wannabet.model.StatoScommessa.VINTA) {
            Optional<Conto> contoOpt = contoRepository.findById(scommessa.getAccount().getIdAccount());

            if (contoOpt.isPresent()) {
                Conto conto = contoOpt.get();
                conto.aggiungiSaldo(scommessa.getVincita()); // Usa il campo `vincita` già presente
                contoRepository.save(conto);
            } else {
                throw new IllegalArgumentException("Conto non trovato per l'account");
            }
        }
    }
}
