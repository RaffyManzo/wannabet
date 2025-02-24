package is.project.wannabet.controller;

import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.model.Conto;
import is.project.wannabet.model.Quota;
import is.project.wannabet.model.TipoScommessa;
import is.project.wannabet.observer.QuotaCache;
import is.project.wannabet.observer.QuotaManager;
import is.project.wannabet.service.AccountRegistratoService;
import is.project.wannabet.service.QuotaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RefertaQuoteController {

    private static final String TEMPLATE = "gestione-referti";

    @Autowired
    private QuotaService quotaService;


    @Autowired
    private AccountRegistratoService accountRegistratoService;

    /**
     * Mostra la pagina con la lista di tutte le quote e una barra di ricerca.
     * Solo gli utenti con ruolo GESTORE_REFERTI possono accedervi.
     */
    @GetMapping("/referta-quote")
    @PreAuthorize("hasRole('GESTORE_REFERTI')")
    public String showRefertaQuotePage(@RequestParam(value="search", required=false) String search,
                                       Model model,
                                       Authentication authentication) {
        // Recupera l'account in base all'email dell'utente loggato (facoltativo, se serve per controlli)
        String email = authentication.getName();
        Optional<AccountRegistrato> accountOpt = accountRegistratoService.getAccountByEmail(email);
        if (accountOpt.isEmpty()) {
            model.addAttribute("error", "Account non trovato");
            return TEMPLATE;
        }

        AccountRegistrato accountRegistrato = accountRegistratoService.getAccountByEmail(authentication.getName()).get();
        Map<AccountRegistrato, Conto> accountMap = new HashMap<>();
        accountMap.put(accountRegistrato,
                new Conto());
        model.addAttribute("accountMap", accountMap);

        // Recupera tutte le quote
        List<Quota> quoteList = quotaService.getAllQuote();


        // Se è stato specificato un termine di ricerca, filtra per nome evento o per esito (esempio)
        if (search != null && !search.isEmpty()) {
            quoteList = quoteList.stream()
                    .filter(q -> q.getEvento().getNome().toLowerCase().contains(search.toLowerCase())
                            || q.getEsito().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("quoteList", quoteList);
        model.addAttribute("search", search);
        return TEMPLATE;
    }

    /**
     * Processa la richiesta di refertazione per una quota.
     */
    @PostMapping("/referta-quote/{idQuota}")
    @PreAuthorize("hasRole('GESTORE_REFERTI')")
    public String refertaQuota(@PathVariable Long idQuota,
                               @RequestParam("referto") String referto,
                               Model model,
                               Authentication authentication) {

        quotaService.loadQuotaInQuotaCache(idQuota);
        quotaService.refertaQuota(idQuota, referto); // Aggiorna lo stato della quota nel database

        // Dopo l'operazione, reindirizza alla pagina per aggiornare la lista
        return "redirect:/referta-quote";
    }
}
