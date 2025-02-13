package is.project.wannabet.security;

import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.service.AccountRegistratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationRequestAccountCheck {

    @Autowired
    private AccountRegistratoService accountRegistratoService;

    public ResponseEntity<?> checkAccount(Long accountId, Authentication authentication) {
        // 🔹 Ottieni l'email dell'utente autenticato
        String userEmail = authentication.getName();

        // 🔹 Recupera l'account dal database
        Optional<AccountRegistrato> accountOpt = accountRegistratoService.getAccountById(accountId);

        // 🔹 Se l'account non esiste, restituisci 404
        if (accountOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account non trovato.");
        }

        AccountRegistrato account = accountOpt.get();

        // 🔹 Verifica che l'utente autenticato stia operando sul proprio account
        if (!account.getEmail().equals(userEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Accesso negato.");
        }

        return ResponseEntity.ok().body("Accesso consentito");
    }
}
