package is.project.wannabet.controller;

import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.model.Conto;
import is.project.wannabet.model.QuotaGiocata;
import is.project.wannabet.model.Scommessa;
import is.project.wannabet.service.AccountRegistratoService;
import is.project.wannabet.service.ContoService;
import is.project.wannabet.service.ScommessaService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ScommessaDetailsController {

    private static final String TEMPLATE = "scommessa-detail";

    @Autowired
    private AccountRegistratoService accountRegistratoService;

    @Autowired
    private ScommessaService scommessaService;

    @Autowired
    private ContoService contoService;

    @GetMapping("/scommessa/{idScommessa}")
    @PreAuthorize("hasAnyRole('UTENTE','ADMIN')")
    public String showScommessa(@PathVariable Long idScommessa, Model model, Authentication authentication) {
        // Recupera l'email dell'utente autenticato (si assume che username sia l'email)
        String email = authentication.getName();

        // Recupera l'account tramite il service
        Optional<AccountRegistrato> accountOpt = accountRegistratoService.getAccountByEmail(email);
        if (accountOpt.isEmpty()) {
            // Se l'account non viene trovato, reindirizza al login
            return "redirect:/login.html";
        }
        AccountRegistrato account = accountOpt.get();

        // Recupera la scommessa tramite l'id e verifica che appartenga all'account
        Optional<Scommessa> scommessaOpt = scommessaService.getScommessaById(idScommessa);
        if (scommessaOpt.isEmpty() ||
                !scommessaOpt.get().getAccount().getIdAccount().equals(account.getIdAccount())) {
            model.addAttribute("error", "Scommessa non trovata o non autorizzata");
            return TEMPLATE;
        }
        Scommessa scommessa = scommessaOpt.get();

        double prodottoQuote = scommessa.getQuoteGiocate().stream()
                .mapToDouble(QuotaGiocata::getMoltiplicatore)
                .reduce(1, (a, b) -> a * b);

        model.addAttribute("quotaTotale", prodottoQuote);

        AccountRegistrato accountRegistrato = accountRegistratoService.getAccountByEmail(authentication.getName()).get();
        Map<AccountRegistrato, Conto> accountMap = new HashMap<>();
        accountMap.put(accountRegistrato,
                contoService.getContoById(accountRegistrato.getConto().getIdConto()).get());
        model.addAttribute("accountMap", accountMap);


        // Aggiungi la scommessa al model per il rendering della view
        model.addAttribute("scommessa", scommessa);

        return TEMPLATE;
    }
}
