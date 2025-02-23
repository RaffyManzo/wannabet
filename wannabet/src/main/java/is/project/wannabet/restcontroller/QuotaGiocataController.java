package is.project.wannabet.restcontroller;

import is.project.wannabet.security.AuthenticationRequestAccountCheck;
import is.project.wannabet.service.QuotaGiocataService;
import is.project.wannabet.service.ScommessaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quota_giocata")
public class QuotaGiocataController {

    @Autowired
    private QuotaGiocataService quotaGiocataService;

    @Autowired
    private ScommessaService scommessaService;

    @Autowired
    private AuthenticationRequestAccountCheck accountCheck;

    @GetMapping("/scommessa/{idScommessa}")
    @PreAuthorize("hasRole('UTENTE')")
    public ResponseEntity<?> getQuoteGiocateByScommessa(@PathVariable Long idScommessa, Authentication authentication) {
        ResponseEntity<?> response = accountCheck.checkAccount(scommessaService.getScommessaById(idScommessa).get().getAccount().getIdAccount(),
                authentication);
        if(response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(quotaGiocataService.getQuoteGiocateByScommessa(idScommessa));
        }
        else return response;
    }
}
