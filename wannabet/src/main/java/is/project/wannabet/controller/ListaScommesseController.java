package is.project.wannabet.controller;

import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.model.Scommessa;
import is.project.wannabet.service.AccountRegistratoService;
import is.project.wannabet.service.ScommessaService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ListaScommesseController {

    private static final String TEMPLATE = "lista-scommesse";

    @Autowired
    private AccountRegistratoService accountRegistratoService;

    @Autowired
    private ScommessaService scommessaService;

    @GetMapping("/lista-scommesse")
    @PreAuthorize("hasRole('UTENTE')")
    public String showListaScommesse(
            @RequestParam(value="type", required=false) String selectedType,
            Model model,
            Authentication authentication) {

        // 1. Recupera l'email dell'utente
        String email = authentication.getName();

        // 2. Recupera l'account
        Optional<AccountRegistrato> accountOpt = accountRegistratoService.getAccountByEmail(email);
        if (accountOpt.isEmpty()) {
            model.addAttribute("error", "Account non trovato");
            return TEMPLATE;
        }
        AccountRegistrato account = accountOpt.get();

        // 3. Recupera tutte le scommesse di questo account
        List<Scommessa> allScommesse = scommessaService.getScommesseByAccount(account.getIdAccount());

        // 4. Se l'utente ha selezionato un type (GIOCATA o PRENOTATA), filtriamo
        if (selectedType != null && !selectedType.isEmpty()) {
            String finalSelectedType = selectedType;
            allScommesse = allScommesse.stream()
                    .filter(s -> s.getTipo().name().equalsIgnoreCase(finalSelectedType))
                    .collect(Collectors.toList());
        } else {
            // se non specificato, consideriamo 'TUTTE'
            selectedType = "ALL";
        }

        // 5. Aggiunge la lista di scommesse filtrate e il tipo selezionato al model
        model.addAttribute("scommesse", allScommesse);
        model.addAttribute("selectedType", selectedType);

        return TEMPLATE;
    }
}
