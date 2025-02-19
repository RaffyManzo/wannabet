package is.project.wannabet.controller;

import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.model.Scommessa;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class ScommessaDetailsController {

    private static final String TEMPLATE = "scommessa-detail";
    private static final String URL_GET_ACCOUNT_BY_EMAIL = "/api/account/"; // L'email verrà concatenata
    private static final String URL_GET_SCOMMESSA = "/api/scommessa/account/{idAccount}/get/{idScommessa}";

    @GetMapping("/scommessa/{idScommessa}")
    @PreAuthorize("hasAnyRole('UTENTE','ADMIN')")
    public String showScommessa(@PathVariable Long idScommessa, Model model, Authentication authentication) {

        // Ottieni l'URL di base del contesto corrente dinamicamente
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .build()
                .toUriString();
        RestTemplate restTemplate = new RestTemplate();

        // Recupera l'email dell'utente dall'Authentication (assumendo che username sia l'email)
        String email = authentication.getName();
        ResponseEntity<AccountRegistrato> accountResponse =
                restTemplate.getForEntity(baseUrl + URL_GET_ACCOUNT_BY_EMAIL + email, AccountRegistrato.class);
        AccountRegistrato account = accountResponse.getBody();

        // Recupera l'id dell'account
        Long idAccount = account.getIdAccount();

        // Chiamata REST per ottenere la scommessa singola tramite idAccount e idScommessa
        ResponseEntity<Scommessa> scommessaResponse =
                restTemplate.getForEntity(baseUrl + URL_GET_SCOMMESSA, Scommessa.class, idAccount, idScommessa);
        Scommessa scommessa = scommessaResponse.getBody();

        // Aggiungi la scommessa al model
        model.addAttribute("scommessa", scommessa);

        return TEMPLATE;
    }
}
