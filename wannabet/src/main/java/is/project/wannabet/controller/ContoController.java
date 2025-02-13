package is.project.wannabet.controller;


import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("#accountId == authentication.principal.idAccount and hasRole('UTENTE')")
    public ResponseEntity<String> deposita(@PathVariable Long accountId,
                                           @RequestParam @Min(value = 1, message = "L'importo deve essere maggiore di zero") double importo) {
        contoService.deposita(accountId, importo);
        return ResponseEntity.ok("Deposito di " + importo + " effettuato con successo.");
    }

    @PostMapping("/{accountId}/preleva")
    @PreAuthorize("#accountId == authentication.principal.idAccount and hasRole('UTENTE')")
    public ResponseEntity<String> preleva(@PathVariable Long accountId,
                                          @RequestParam @Min(value = 1, message = "L'importo deve essere maggiore di zero") double importo) {
        if(contoService.preleva(accountId, importo))
            return ResponseEntity.ok("Prelievo di " + importo + " effettuato con successo.");
        else
            return ResponseEntity.badRequest().body("Saldo insufficinte");
    }

    @GetMapping("/{accountId}/verifica-saldo")
    @PreAuthorize("hasRole('UTENTE')")
    public ResponseEntity<Boolean> verificaSaldo(@PathVariable Long accountId, @RequestParam double importo) {
        boolean saldoSufficiente = contoService.verificaSaldo(accountId, importo);
        return ResponseEntity.ok(saldoSufficiente);
    }

}

