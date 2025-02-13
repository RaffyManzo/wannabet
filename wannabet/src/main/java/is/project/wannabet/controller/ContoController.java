package is.project.wannabet.controller;


import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.security.AuthenticationRequestAccountCheck;
import is.project.wannabet.service.AccountRegistratoService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import is.project.wannabet.model.Conto;
import is.project.wannabet.service.ContoService;

import java.util.List;
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

    @PostMapping
    @PreAuthorize("hasRole('UTENTE')")
    public Conto createConto(@RequestBody Conto conto) {
        return contoService.saveConto(conto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('UTENTE')")
    public void deleteConto(@PathVariable Long id) {
        contoService.deleteConto(id);
    }

    @PostMapping("/{accountId}/deposita")
    @PreAuthorize("hasRole('UTENTE')")
    public ResponseEntity<?> deposita(@PathVariable Long accountId,
                                           @RequestParam @Min(value = 1, message = "L'importo deve essere maggiore di zero") double importo,
                                           Authentication authentication) {

        ResponseEntity<?> response = accountCheck.checkAccount(accountId, authentication);

        if(response.getStatusCode() == HttpStatus.OK) {
            // 🔹 Esegui il deposito
            contoService.deposita(accountId, importo);
            return ResponseEntity.ok("Deposito di " + importo + " effettuato con successo.");
        } else return response;
    }


    @PostMapping("/{accountId}/preleva")
    @PreAuthorize("hasRole('UTENTE')")
    public ResponseEntity<?> preleva(@PathVariable Long accountId,
                                          @RequestParam @Min(value = 1, message = "L'importo deve essere maggiore di zero") double importo,
                                          Authentication authentication) {

        ResponseEntity<?> response = accountCheck.checkAccount(accountId, authentication);

        if(response.getStatusCode() == HttpStatus.OK) {
            if (contoService.preleva(accountId, importo))
                return ResponseEntity.ok("Prelievo di " + importo + " effettuato con successo.");
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

