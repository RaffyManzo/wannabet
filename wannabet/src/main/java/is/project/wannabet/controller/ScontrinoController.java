package is.project.wannabet.controller;

import is.project.wannabet.model.Quota;
import is.project.wannabet.model.Scontrino;
import is.project.wannabet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/scontrino")
@SessionAttributes("scontrino")
public class ScontrinoController {

    @Autowired
    private ScommessaService scommessaService;

    @Autowired
    private QuotaService quotaService;

    @Autowired
    private ContoService contoService;

    @Autowired
    private AccountRegistratoService accountRegistratoService;

    /**
     * Crea automaticamente lo scontrino alla creazione della sessione.
     */
    @ModelAttribute("scontrino")
    public Scontrino creaScontrino() {
        return new Scontrino();
    }

    /**
     * Recupera lo stato attuale dello scontrino.
     */
    @GetMapping
    public ResponseEntity<Scontrino> getScontrino(@ModelAttribute("scontrino") Scontrino scontrino) {
        return ResponseEntity.ok(scontrino);
    }

    /**
     * Aggiunge una quota allo scontrino e aggiorna la sessione.
     */
    @PostMapping("/aggiungi/{id}")
    public ResponseEntity<Scontrino> aggiungiQuota(@ModelAttribute("scontrino") Scontrino scontrino,
                                                   @PathVariable Long id,
                                                   Model model) {
        // TODO: da verificare se bisogna usare QuotaManager
        Optional<Quota> quotaOptional = quotaService.getQuotaById(id);

        if (quotaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(scontrino);
        }

        Quota quota = quotaOptional.get();
        if (quota.isChiusa()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(scontrino);
        }

        scontrino.aggiungiQuota(quota);

        // **Aggiorniamo la sessione con il nuovo scontrino**
        model.addAttribute("scontrino", scontrino);

        return ResponseEntity.ok(scontrino);
    }

    /**
     * Rimuove una quota dallo scontrino e aggiorna la sessione.
     */
    @DeleteMapping("/rimuovi/{id}")
    public ResponseEntity<Scontrino> rimuoviQuota(@ModelAttribute("scontrino") Scontrino scontrino,
                                                  @PathVariable Long id,
                                                  Model model) {

        // TODO: da verificare se bisogna usare QuotaManager

        Optional<Quota> quotaOptional = quotaService.getQuotaById(id);

        if (quotaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(scontrino);
        }

        Quota quota = quotaOptional.get();
        scontrino.rimuoviQuota(quota);

        // **Forza l'aggiornamento della sessione rimuovendo e ri-aggiungendo l'oggetto**
        model.asMap().remove("scontrino");
        model.addAttribute("scontrino", scontrino);

        return ResponseEntity.ok(scontrino);
    }

    /**
     * Svuota completamente lo scontrino e aggiorna la sessione.
     */
    @DeleteMapping("/svuota")
    public ResponseEntity<String> svuotaScontrino(Model model) {
        model.addAttribute("scontrino", new Scontrino());
        return ResponseEntity.ok("Scontrino svuotato.");
    }

    /**
     * Conferma la scommessa e la registra nel sistema.
     */
    @PostMapping("/conferma")
    public ResponseEntity<String> confermaScommessa(@ModelAttribute("scontrino") Scontrino scontrino,
                                                    @RequestParam double importo,
                                                    @RequestParam Long idAccount,
                                                    Model model) {
        if (scontrino.getQuote().isEmpty()) {
            return ResponseEntity.badRequest().body("Lo scontrino è vuoto, impossibile piazzare una scommessa.");
        }

        // Verifica la presenza di quote chiuse
        List<Quota> quoteNonValide = scontrino.getQuote().stream()
                .filter(Quota::isChiusa)
                .toList();

        if (!quoteNonValide.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Lo scontrino contiene quote chiuse.");
        }

        // Recupero conto associato all'utente

        // TODO: il conto deve essre rcuperato dalla sessione
        Optional<Long> idContoOpt = accountRegistratoService.getAccountById(idAccount)
                .map(account -> account.getConto().getIdConto());

        if (idContoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account non trovato.");
        }

        Long idConto = idContoOpt.get();
        if (!contoService.verificaSaldo(idConto, importo)) {
            return ResponseEntity.badRequest().body("Saldo insufficiente per piazzare la scommessa.");
        }

        // Creazione della scommessa
        scommessaService.creaScommessa(scontrino.getQuote(), importo, idAccount);

        // **Svuota lo scontrino dopo la conferma e aggiorna la sessione**
        scontrino.svuotaScontrino();
        model.addAttribute("scontrino", scontrino);

        return ResponseEntity.ok("Scommessa effettuata con successo!");
    }
}
