package is.project.wannabet.controller;

import is.project.wannabet.model.QuotaGiocata;
import is.project.wannabet.service.QuotaGiocataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quota_giocata")
public class QuotaGiocataController {

    @Autowired
    private QuotaGiocataService quotaGiocataService;

    @GetMapping("/scommessa/{idScommessa}")
    public ResponseEntity<List<QuotaGiocata>> getQuoteGiocateByScommessa(@PathVariable Long idScommessa) {
        return ResponseEntity.ok(quotaGiocataService.getQuoteGiocateByScommessa(idScommessa));
    }
}
