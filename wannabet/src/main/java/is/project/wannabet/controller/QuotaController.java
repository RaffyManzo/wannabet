package is.project.wannabet.controller;

import is.project.wannabet.model.Scommessa;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @param idQuota ID della quota da refertare.
     * @param referto Referto assegnato alla quota.
     * @return ResponseEntity con stato HTTP appropriato.
     */
    @PostMapping("/quota/referta/{id}")
    public ResponseEntity<Void> refertaQuota(@PathVariable Long idQuota, @RequestParam String referto) {
        Optional<Quota> quotaOpt = quotaService.getQuotaById(idQuota);

        if (quotaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        quotaService.refertaQuota(idQuota, referto);
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
