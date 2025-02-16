package is.project.wannabet.controller;

import is.project.wannabet.model.Quota;
import is.project.wannabet.service.QuotaService;
import is.project.wannabet.observer.QuotaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quota")
public class QuotaController {

    @Autowired
    private QuotaService quotaService;

    @Autowired
    private is.project.wannabet.observer.QuotaCache quotaCache;

    @Autowired
    private QuotaManager quotaManager;

    /**
     * Endpoint per refertare una quota dato il suo ID.
     * @param id ID della quota da refertare.
     * @param referto Referto assegnato alla quota.
     * @return ResponseEntity con stato HTTP appropriato.
     */
    @PostMapping("/referta/{id}")
    @PreAuthorize("hasAnyRole('GESTORE_REFERTI', 'ADMIN')")
    public ResponseEntity<Void> refertaQuota(@PathVariable Long id, @RequestParam String referto) {
        Optional<Quota> quotaOpt = quotaService.getQuotaById(id);

        if (quotaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Restituisce 404 se la quota non esiste
        }

        Quota quota = quotaOpt.get();
        quotaService.refertaQuota(id, referto); // Aggiorna lo stato della quota nel database
        quotaManager.aggiornaQuota(quota); // Notifica gli observer in tempo reale

        return ResponseEntity.ok().build();
    }

    /**
     * Recupera tutte le quote disponibili.
     */

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('GESTORE_EVENTO', 'ADMIN')")
    public List<Quota> getAllQuote() {
        return quotaService.getAllQuote();
    }

    /**
     * Recupera una quota specifica per ID.
     */
    @GetMapping("/{id}")
    public Optional<Quota> getQuotaById(@PathVariable Long id) {
        return quotaService.getQuotaById(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('GESTORE_EVENTO', 'ADMIN')")
    public ResponseEntity<Quota> updateQuota(@PathVariable Long id, @RequestBody Quota quotaDetails) {
        Quota updatedQuota = quotaService.updateQuota(id, quotaDetails);
        return ResponseEntity.ok(updatedQuota);
    }


    /**
     * Recupera tutte le quote associate a un evento.
     */
    @GetMapping("/evento/{eventoId}")
    public List<Quota> getQuoteByEvento(@PathVariable Long eventoId) {
        return quotaService.getQuoteByEvento(eventoId);
    }

    /**
     * Crea una nuova quota e la registra nel sistema di monitoraggio.
     */
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('GESTORE_EVENTO', 'ADMIN')")
    public Quota createQuota(@RequestBody Quota quota) {
        Quota savedQuota = quotaService.saveQuota(quota); // Salva la quota nel database
        quotaManager.aggiornaQuota(savedQuota); // Registra la quota nel sistema di monitoraggio in tempo reale
        return savedQuota;
    }

    // TODO: getAllQuoteOfEventoGroupedByCategoria

    /**
     * Elimina una quota dal database e dal monitoraggio.
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('GESTORE_EVENTO', 'ADMIN')")
    public ResponseEntity<String> deleteQuota(@PathVariable Long id) {
        Optional<Quota> quotaOpt = quotaService.getQuotaById(id);

        if (quotaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quota non trovata.");
        }

        quotaService.deleteQuota(id); // Elimina la quota dal database
        quotaCache.rimuoviQuota(id); // Rimuove la quota dal sistema di monitoraggio

        return ResponseEntity.ok("Quota eliminata con successo.");
    }
}
