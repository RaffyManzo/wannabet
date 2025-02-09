package is.project.wannabet.controller;

import is.project.wannabet.model.Quota;
import is.project.wannabet.model.Scontrino;
import is.project.wannabet.service.ScommessaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@RestController
@RequestMapping("/api/scontrino")
@SessionAttributes("scontrino")
public class ScontrinoController {

    @Autowired
    private ScommessaService scommessaService;

    /**
     * Crea automaticamente lo scontrino alla creazione della sessione.
     */
    @ModelAttribute("scontrino")
    public Scontrino creaScontrino() {
        return new Scontrino();
    }

    @GetMapping
    public ResponseEntity<Scontrino> getScontrino(@ModelAttribute("scontrino") Scontrino scontrino) {
        return ResponseEntity.ok(scontrino);
    }

    @PostMapping("/aggiungi")
    public ResponseEntity<String> aggiungiQuota(@ModelAttribute("scontrino") Scontrino scontrino, @RequestBody Quota quota) {
        scontrino.aggiungiQuota(quota);
        return ResponseEntity.ok("Quota aggiunta allo scontrino.");
    }

    @PostMapping("/rimuovi")
    public ResponseEntity<String> rimuoviQuota(@ModelAttribute("scontrino") Scontrino scontrino, @RequestBody Quota quota) {
        scontrino.rimuoviQuota(quota);
        return ResponseEntity.ok("Quota rimossa dallo scontrino.");
    }

    @DeleteMapping("/svuota")
    public ResponseEntity<String> svuotaScontrino(Model model) {
        model.addAttribute("scontrino", new Scontrino()); // Reinizializza lo scontrino alla sessione corrente
        return ResponseEntity.ok("Scontrino svuotato.");
    }

    @PostMapping("/conferma")
    public ResponseEntity<String> confermaScommessa(@ModelAttribute("scontrino") Scontrino scontrino,
                                                    @RequestParam double importo) {
        if (scontrino.getQuote().isEmpty()) {
            return ResponseEntity.badRequest().body("Lo scontrino è vuoto, impossibile piazzare una scommessa.");
        }

        scommessaService.creaScommessaDaScontrino(scontrino.getQuote(), importo);
        scontrino.svuotaScontrino(); // Dopo la conferma, svuotiamo lo scontrino
        return ResponseEntity.ok("Scommessa effettuata con successo!");
    }
}
