package is.project.wannabet.restcontroller;

import is.project.wannabet.model.Quota;
import is.project.wannabet.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @GetMapping("/{codice}")
    public ResponseEntity<List<Quota>> getQuoteByPrenotazione(@PathVariable String codice) {
        List<Quota> quote = prenotazioneService.getQuoteByPrenotazione(codice);
        return quote.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(quote);
    }
}
