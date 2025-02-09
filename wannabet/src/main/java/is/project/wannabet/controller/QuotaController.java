package is.project.wannabet.controller;

import is.project.wannabet.model.Scommessa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import is.project.wannabet.model.Quota;
import is.project.wannabet.service.QuotaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quota")
public class QuotaController {

    @Autowired
    private QuotaService quotaService;

    /**
     * Endpoint per refertare una quota dato il suo ID.
     * @param id ID della quota da refertare.
     * @param referto Referto assegnato alla quota.
     * @return ResponseEntity con stato HTTP appropriato.
     */
    @PostMapping("/referta/{id}")
    public ResponseEntity<Void> refertaQuota(@PathVariable Long id, @RequestParam String referto) {
        Optional<Quota> quotaOpt = quotaService.getQuotaById(id);

        if (quotaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Restituisce 404 se la quota non esiste
        }

        quotaService.refertaQuota(id, referto);
        return ResponseEntity.ok().build();
    }



    @GetMapping
    public List<Quota> getAllQuote() {
        return quotaService.getAllQuote();
    }

    @GetMapping("/{id}")
    public Optional<Quota> getQuotaById(@PathVariable Long id) {
        return quotaService.getQuotaById(id);
    }

    @GetMapping("/evento/{eventoId}")
    public List<Quota> getQuoteByEvento(@PathVariable Long eventoId) {
        return quotaService.getQuoteByEvento(eventoId);
    }

    @PostMapping
    public Quota createQuota(@RequestBody Quota quota) {
        return quotaService.saveQuota(quota);
    }

    @DeleteMapping("/{id}")
    public void deleteQuota(@PathVariable Long id) {
        quotaService.deleteQuota(id);
    }


}
