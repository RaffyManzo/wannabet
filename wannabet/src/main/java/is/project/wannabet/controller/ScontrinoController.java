package is.project.wannabet.controller;

import is.project.wannabet.factory.ScommessaFactory;
import is.project.wannabet.model.*;
import is.project.wannabet.security.AuthenticationRequestAccountCheck;
import is.project.wannabet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
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
    private PrenotazioneService prenotazioneService;

    @Autowired
    private AuthenticationRequestAccountCheck accountCheck;

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
    @PreAuthorize("hasRole('UTENTE')")
    public ResponseEntity<Scontrino> getScontrino(@ModelAttribute("scontrino") Scontrino scontrino) {
        return ResponseEntity.ok(scontrino);
    }

    /**
     * Aggiunge una quota allo scontrino e aggiorna la sessione.
     */
    @PostMapping("/aggiungi/{id}")
    @PreAuthorize("hasRole('UTENTE')")
    public ResponseEntity<Scontrino> aggiungiQuota(@ModelAttribute("scontrino") Scontrino scontrino,
                                                   @PathVariable Long id,
                                                   Model model) {
        Optional<Quota> quotaOptional = quotaService.getQuotaById(id);

        if (quotaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(scontrino);
        }

        Quota quota = quotaOptional.get();

        scontrino.aggiungiQuota(quota);
        model.addAttribute("scontrino", scontrino);

        return ResponseEntity.ok(scontrino);
    }



    /**
     * Rimuove una quota dallo scontrino e aggiorna la sessione.
     */
    @DeleteMapping("/rimuovi/{id}")
    @PreAuthorize("hasRole('UTENTE')")
    public ResponseEntity<Scontrino> rimuoviQuota(@ModelAttribute("scontrino") Scontrino scontrino,
                                                  @PathVariable Long id,
                                                  Model model) {


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
    @PreAuthorize("hasRole('UTENTE')")
    public ResponseEntity<String> svuotaScontrino(Model model) {
        model.addAttribute("scontrino", new Scontrino());
        return ResponseEntity.ok("Scontrino svuotato.");
    }

    /**
     * Conferma la scommessa e la registra nel sistema.
     */
    @PostMapping("/conferma")
    @PreAuthorize("hasRole('UTENTE')")
    public ResponseEntity<?> confermaScommessa(@ModelAttribute("scontrino") Scontrino scontrino,
                                                    @RequestParam double importo,
                                                    Model model, Authentication authentication) {
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

        AccountRegistratoDetails accountDetails = (AccountRegistratoDetails) authentication.getPrincipal();
        AccountRegistrato account = accountDetails.getAccount();


        ResponseEntity<?> response = accountCheck.checkAccount(account.getIdAccount(), authentication);

        if(response.getStatusCode() == HttpStatus.OK) {

            if (!contoService.verificaSaldo(account.getConto().getIdConto(), importo)) {
                return ResponseEntity.badRequest().body("Saldo insufficiente per piazzare la scommessa.");
            }

            // Creazione della scommessa
            scommessaService.creaScommessa(scontrino.getQuote(), importo, account.getIdAccount());

            // **Svuota lo scontrino dopo la conferma e aggiorna la sessione**
            scontrino.svuotaScontrino();
            model.addAttribute("scontrino", scontrino);

            return ResponseEntity.ok("Scommessa effettuata con successo!");
        } else
            return response;
    }

    @PostMapping("/prenota")
    @PreAuthorize("hasRole('UTENTE')")
    public ResponseEntity<?> prenotacommessa(@ModelAttribute("scontrino") Scontrino scontrino,
                                             @RequestParam double importo,
                                             Authentication authentication) {

        if (scontrino.getQuote().isEmpty()) {
            return ResponseEntity.badRequest().body("Lo scontrino è vuoto, impossibile piazzare una scommessa.");
        }

        AccountRegistratoDetails accountDetails = (AccountRegistratoDetails) authentication.getPrincipal();
        AccountRegistrato account = accountDetails.getAccount();


        ResponseEntity<?> response = accountCheck.checkAccount(account.getIdAccount(), authentication);

        if(response.getStatusCode() == HttpStatus.OK) {
            Scommessa scommessa = ScommessaFactory.createScommessaPrenotata(accountRegistratoService.getAccountById(account.getIdAccount()).get(),
                    scontrino.getQuote(), importo);


            scommessaService.saveScommessa(scommessa);

            String codice = prenotazioneService.generaCodiceUnico();

            Prenotazione prenotazione = new Prenotazione(codice, scommessa);

            prenotazione = prenotazioneService.savePrenotazione(prenotazione);

            return ResponseEntity.status(HttpStatus.CREATED).body(prenotazione.getCodice());
        } else
            return response;
    }
}
