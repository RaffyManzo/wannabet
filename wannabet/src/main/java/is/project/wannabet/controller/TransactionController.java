package is.project.wannabet.controller;

import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.payload.DepositoRequest;
import is.project.wannabet.service.AccountRegistratoService;
import is.project.wannabet.service.ContoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TransactionController {

    @Autowired
    private AccountRegistratoService accountRegistratoService;

    @GetMapping("/deposita")
    @PreAuthorize("hasRole('UTENTE')")
    public String showDepositoPage(Model model, Authentication authentication) {
        // Estrae l'account dall'Authentication (si assume che sia di tipo AccountRegistrato)
        AccountRegistrato account = accountRegistratoService.getAccountByEmail(authentication.getName()).get();
        model.addAttribute("accountId", account.getIdAccount());
        return "deposito";
    }
}
