package is.project.wannabet.controller;

import is.project.wannabet.model.Evento;
import is.project.wannabet.model.Quota;
import is.project.wannabet.model.StatoQuota;
import is.project.wannabet.service.EventoService;
import is.project.wannabet.service.QuotaService;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ListaQuoteEventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private QuotaService quotaService;

    /**
     * Endpoint che gestisce sia il caso in cui viene passato un eventId (singolo evento)
     * sia il caso in cui viene passata una descrizione (più eventi).
     * Se viene passato anche il parametro "categoria", vengono visualizzate solo le quote della categoria selezionata.
     */

    @GetMapping("/quote")
    public String showQuotes(
            @RequestParam(value = "event", required = false) Long eventId,
            @RequestParam(value = "descrizione", required = false) String descrizione,
            @RequestParam(value = "categoria", required = false) String categoriaParam,
            Model model) {

        List<Evento> eventi = new ArrayList<>();

        if (eventId != null) {
            eventoService.getEventoById(eventId).ifPresent(eventi::add);
        } else if (descrizione != null && !descrizione.trim().isEmpty()) {
            eventi = eventoService.getEventiByDescrizione(descrizione);
        }

        // Calcola l'insieme di tutte le categorie (non filtrate) provenienti dalle quote degli eventi
        Set<String> categorieSet = new LinkedHashSet<>();
        for (Evento e : eventi) {
            List<Quota> tutteQuote = quotaService.getQuoteByEvento(e.getIdEvento());
            tutteQuote.forEach(q -> categorieSet.add(q.getCategoria()));
        }
        List<String> categorieQuote = new ArrayList<>(categorieSet);

        // Determina la categoria selezionata: se viene passato un parametro, usalo; altrimenti, la prima disponibile
        String categoriaSelezionata = (categoriaParam != null && !categoriaParam.trim().isEmpty())
                ? categoriaParam
                : (!categorieQuote.isEmpty() ? categorieQuote.get(0) : "");

        // Per ogni evento, ottieni le quote e, se è stata selezionata una categoria, filtra per essa
        Map<Long, List<Quota>> quotePerEvento = new HashMap<>();
        for (Evento e : eventi) {
            List<Quota> quotes = quotaService.getQuoteByEvento(e.getIdEvento());

            quotes = quotes.stream()
                    .filter(q -> !q.isChiusa() && q.getStato() == StatoQuota.DA_REFERTARE)
                    .collect(Collectors.toList());

            if (categoriaSelezionata != null && !categoriaSelezionata.isEmpty()) {
                quotes = quotes.stream()
                        .filter(q -> q.getCategoria().equalsIgnoreCase(categoriaSelezionata))
                        .collect(Collectors.toList());
            }
            quotePerEvento.put(e.getIdEvento(), quotes);
        }

        model.addAttribute("eventi", eventi);
        model.addAttribute("quotePerEvento", quotePerEvento);
        model.addAttribute("categorieQuote", categorieQuote);
        model.addAttribute("categoriaSelezionata", categoriaSelezionata);

        return "quote"; // template Thymeleaf "quote.html"
    }
}
