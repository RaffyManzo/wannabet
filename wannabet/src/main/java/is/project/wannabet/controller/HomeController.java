package is.project.wannabet.controller;

import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.model.Conto;
import is.project.wannabet.model.Evento;
import is.project.wannabet.model.Quota;
import is.project.wannabet.service.AccountRegistratoService;
import is.project.wannabet.service.ContoService;
import is.project.wannabet.service.EventoService;
import is.project.wannabet.service.QuotaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private static final String TEMPLATE_STRING_RESOURCES = "homepage";
    @Autowired
    private EventoService eventoService;

    @Autowired
    private QuotaService quotaService;

    @Autowired
    private AccountRegistratoService accountRegistratoService;

    @Autowired
    private ContoService contoService;


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/home")
    public String showHome(@RequestParam(value = "cat", required = false) String selectedCategory, Model model, HttpServletRequest request) {
        // Controllo della sessione e dell'utente autenticato
        Principal userPrincipal = request.getUserPrincipal();
        System.out.println("Session ID: " + request.getSession().getId());
        System.out.println("Utente autenticato: " + (userPrincipal != null ? userPrincipal.getName() : "Nessuno"));

        if (userPrincipal == null) {
            return "redirect:/login.html";
        }

        // Ottieni dinamicamente le categorie e le competizioni da eventoService
        // La mappa ha struttura: { "Calcio": [ "CHAMPIONS LEAGUE", "EUROPA LEAGUE", ... ], "Basket": [...], ... }
        Map<String, java.util.List<String>> eventDescriptions = eventoService.getAllDescrizioniGroupedByCategoria();
        if (eventDescriptions == null) {
            eventDescriptions = new LinkedHashMap<>();
        }

        // Se selectedCategory non è passato o non esiste nella mappa, usa la prima categoria disponibile
        if (selectedCategory == null || !eventDescriptions.containsKey(selectedCategory)) {
            if (!eventDescriptions.isEmpty()) {
                selectedCategory = eventDescriptions.keySet().iterator().next();
            } else {
                selectedCategory = "";
            }
        }

        // Passa al model le categorie, la lista di competizioni per la categoria selezionata
        model.addAttribute("allCategories", eventDescriptions.keySet());
        model.addAttribute("selectedCategory", selectedCategory);
        model.addAttribute("competitionLabels", eventDescriptions.get(selectedCategory));

        // Ottieni la mappa degli eventi imminenti e relative quote per la categoria selezionata
        Map<Evento, java.util.List<Quota>> upcomingEventsMap = quotaService.getTop6UpcomingEventsAndQuotes(selectedCategory);

        // Filtra gli eventi che sono chiusi (supponendo che Evento abbia il metodo isChiuso())
        upcomingEventsMap = upcomingEventsMap.entrySet().stream()
                .filter(entry -> !entry.getKey().isChiuso())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));


        AccountRegistrato accountRegistrato = accountRegistratoService.getAccountByEmail(userPrincipal.getName()).get();
        Map<AccountRegistrato, Conto> accountMap = new HashMap<>();
        accountMap.put(accountRegistrato,
                contoService.getContoById(accountRegistrato.getConto().getIdConto()).get());
        model.addAttribute("accountMap", accountMap);

        model.addAttribute("upcomingEventsMap", upcomingEventsMap);


        return TEMPLATE_STRING_RESOURCES;
    }

    @GetMapping("/debug/session")
    public ResponseEntity<String> checkSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Nessuna sessione trovata");
        }
        return ResponseEntity.ok("✅ Sessione attiva con ID: " + session.getId());
    }



}
