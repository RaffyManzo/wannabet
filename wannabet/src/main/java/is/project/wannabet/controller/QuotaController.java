package is.project.wannabet.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
