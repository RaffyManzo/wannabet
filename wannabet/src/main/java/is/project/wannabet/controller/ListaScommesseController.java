package is.project.wannabet.controller;

import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.model.Scommessa;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Controller
public class ListaScommesseController {

    private static final String TEMPLATE = "lista-scommesse";
    private static final String URL_GET_ACCOUNT_BY_EMAIL = "/api/account/"; // L'email verrà concatenata
    private static final String URL_GET_SCOMMESSE_BY_ACCOUNT = "/api/scommesse/account/";


    @GetMapping("/lista-scommesse")
    @PreAuthorize("hasRole('UTENTE')")
    public String showListaScommesse(Model model, Authentication authentication) {



        // Recupera l'email dell'utente dall'oggetto Authentication (assumendo che username sia l'email)
        String email = authentication.getName();

        // Chiamata REST per ottenere l'account tramite email

        // Chiamata REST per ottenere le scommesse dell'utente tramite l'id dell'account

        // Aggiungi la lista di scommesse al model

        return TEMPLATE;
    }
}
