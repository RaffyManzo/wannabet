package is.project.wannabet.restcontroller;

import is.project.wannabet.model.*;
import is.project.wannabet.security.AuthenticationRequestAccountCheck;
import is.project.wannabet.service.AccountRegistratoService;
import is.project.wannabet.service.ContoService;
import is.project.wannabet.service.ScommessaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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


    @Autowired
    private AuthenticationRequestAccountCheck accountCheck;

    @Autowired
    private AccountRegistratoService accountRegistratoService;

    @Autowired
    private ContoService contoService;

    /**
     * Recupera tutte le scommesse associate a un determinato account.
     *
     * @param idAccount ID dell'account di cui recuperare le scommesse.
     * @return Lista di scommesse dell'account specificato.
     */
    @GetMapping("/account/{idAccount}")

    @PreAuthorize("hasRole('UTENTE')")
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

    @PreAuthorize("hasAnyRole('UTENTE', 'ADMIN')")
    public ResponseEntity<?> getScommessaById(@PathVariable Long idAccount, @PathVariable Long idScommessa,
                                                      Authentication authentication) {

        ResponseEntity<?> response = accountCheck.checkAccount(idAccount, authentication);

        if(response.getStatusCode() == HttpStatus.OK ||
                authentication.getAuthorities().stream()
                        .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {


            Optional<Scommessa> scommessaOpt = scommessaService.getScommessaById(idScommessa);

            if (scommessaOpt.isPresent() && scommessaOpt.get().getAccount().getIdAccount().equals(idAccount)) {
                return ResponseEntity.ok(scommessaOpt.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        else return response;
    }

    /**
     * Crea una nuova scommessa per un determinato account.
     *
     * @param idAccount ID dell'account per cui creare la scommessa.
     * @param scommessa L'oggetto scommessa da salvare.
     * @return La scommessa creata con stato HTTP 201 Created.
     */
    @PostMapping("/{idAccount}/crea")

    @PreAuthorize("hasRole('UTENTE')")
    public ResponseEntity<?> createScommessa(@PathVariable Long idAccount,
                                             @RequestBody Scommessa scommessa,
                                             Authentication authentication) {

        ResponseEntity<?> response = accountCheck.checkAccount(idAccount, authentication);

        if(response.getStatusCode() == HttpStatus.OK) {

                if (!contoService.verificaSaldo(idAccount, scommessa.getImporto())) {
                    return ResponseEntity.status(HttpStatus.resolve(400)).body(Map.of("error", "Saldo insufficiente"));
                }

                // 3️⃣ Scala l'importo dal saldo
                contoService.preleva(idAccount, scommessa.getImporto());

                Scommessa nuovaScommessa = scommessaService.saveScommessa(scommessa);
                return ResponseEntity.status(HttpStatus.CREATED).body(nuovaScommessa);
            }
        else return response;
    }



}
