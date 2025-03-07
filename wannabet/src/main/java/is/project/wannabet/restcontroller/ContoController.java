package is.project.wannabet.restcontroller;


import is.project.wannabet.payload.DepositoRequest;
import is.project.wannabet.payload.PrelievoRequest;
import is.project.wannabet.security.AuthenticationRequestAccountCheck;
import is.project.wannabet.service.AccountRegistratoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import is.project.wannabet.model.Conto;
import is.project.wannabet.service.ContoService;

import java.util.Optional;

@RestController
@RequestMapping("/api/conto")
public class ContoController {

    @Autowired
    private ContoService contoService;


    @Autowired
    private AuthenticationRequestAccountCheck accountCheck;

    @Autowired
    private AccountRegistratoService accountRegistratoService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('UTENTE')")
    public Optional<Conto> getContoById(@PathVariable Long id) {
        return contoService.getContoById(id);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('UTENTE')")
    public void deleteConto(@PathVariable Long id) {
        contoService.deleteConto(id);
    }

    @PostMapping("/{accountId}/deposita")
    @PreAuthorize("hasRole('UTENTE')")
    public ResponseEntity<?> deposita(@PathVariable Long accountId,
                                      @Valid @RequestBody DepositoRequest depositoRequest,
                                      Authentication authentication) {

        ResponseEntity<?> response = accountCheck.checkAccount(accountId, authentication);

        if(response.getStatusCode() == HttpStatus.OK) {
            double importo = depositoRequest.getImporto();
            contoService.deposita(accountId, importo);
            return ResponseEntity.ok("Deposito di " + importo + " effettuato con successo.");
        } else {
            return response;
        }
    }



    @PostMapping("/{accountId}/preleva")
    @PreAuthorize("hasRole('UTENTE')")
    public ResponseEntity<?> preleva(@PathVariable Long accountId,
                                     @Valid @RequestBody PrelievoRequest prelievoRequest,
                                          Authentication authentication) {

        ResponseEntity<?> response = accountCheck.checkAccount(accountId, authentication);

        if(response.getStatusCode() == HttpStatus.OK) {
            if (contoService.preleva(accountId, prelievoRequest.getImporto()))
                return ResponseEntity.ok("Prelievo di " + prelievoRequest.getImporto() + " effettuato con successo all'Iban: " + prelievoRequest.getIban());
            else
                return ResponseEntity.badRequest().body("Saldo insufficinte");
        } else return response;
    }

    @GetMapping("/{accountId}/verifica-saldo")
    @PreAuthorize("hasRole('UTENTE')")
    public ResponseEntity<Boolean> verificaSaldo(@PathVariable Long accountId, @RequestParam double importo) {
        boolean saldoSufficiente = contoService.verificaSaldo(accountId, importo);
        return ResponseEntity.ok(saldoSufficiente);
    }

}

