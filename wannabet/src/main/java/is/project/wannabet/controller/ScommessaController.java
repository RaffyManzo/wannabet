package is.project.wannabet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import is.project.wannabet.model.Scommessa;
import is.project.wannabet.service.ScommessaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST per la gestione delle scommesse.
 * Tutte le scommesse sono legate a un utente specifico.
 */
@RestController
@RequestMapping("/api/scommesse")
public class ScommessaController {

    @Autowired
    private ScommessaService scommessaService;

    /**
     * Recupera tutte le scommesse associate a un determinato account.
     *
     * @param idAccount ID dell'account di cui recuperare le scommesse.
     * @return Lista di scommesse dell'account specificato.
     */
    @GetMapping("/account/{idAccount}")
    public ResponseEntity<List<Scommessa>> getScommesseByAccount(@PathVariable Long idAccount) {
        List<Scommessa> scommesse = scommessaService.getScommesseByAccount(idAccount);
        return ResponseEntity.ok(scommesse);
    }

    /**
     * Recupera una scommessa specifica solo se appartiene all'account richiesto.
     *
     * @param idAccount   ID dell'account proprietario della scommessa.
     * @param idScommessa ID della scommessa da recuperare.
     * @return La scommessa se trovata e appartiene all'account, altrimenti 404.
     */
    @GetMapping("/account/{idAccount}/get/{idScommessa}")
    public ResponseEntity<Scommessa> getScommessaById(@PathVariable Long idAccount, @PathVariable Long idScommessa) {
        Optional<Scommessa> scommessaOpt = scommessaService.getScommessaById(idScommessa);

        if (scommessaOpt.isPresent() && scommessaOpt.get().getAccount().getIdAccount().equals(idAccount)) {
            return ResponseEntity.ok(scommessaOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Crea una nuova scommessa per un determinato account.
     *
     * @param idAccount ID dell'account per cui creare la scommessa.
     * @param scommessa L'oggetto scommessa da salvare.
     * @return La scommessa creata con stato HTTP 201 Created.
     */
    @PostMapping("/{idAccount}/crea")
    public ResponseEntity<Scommessa> createScommessa(@PathVariable Long idAccount,
                                                     @RequestBody Scommessa scommessa) {
        Scommessa nuovaScommessa = scommessaService.saveScommessa(scommessa);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuovaScommessa);
    }


}
