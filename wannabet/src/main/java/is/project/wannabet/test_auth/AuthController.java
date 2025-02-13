package is.project.wannabet.test_auth;

import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.model.TipoAccount;
import is.project.wannabet.repository.AccountRegistratoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AccountRegistratoRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AccountRegistratoRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registrazione di un nuovo utente.
     */
    @PostMapping("/register")
    public String register(@RequestBody AccountRegistrato account) {
        Optional<AccountRegistrato> existingUser = accountRepository.findByEmail(account.getEmail());
        if (existingUser.isPresent()) {
            return "Errore: Email già registrata!";
        }

        // 🔹 Cripta la password prima di salvarla
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setTipo(TipoAccount.UTENTE); // Imposta il ruolo di default

        accountRepository.save(account);
        return "Utente registrato con successo!";
    }

    /**
     * Login: Spring Security gestisce automaticamente l'autenticazione.
     * Se l'utente è autenticato, carichiamo i suoi dati nella sessione.
     */
    @PostMapping("/login")
    public String login(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return "Errore: Credenziali non valide!";
        }

        // 🔹 Carica l'utente autenticato nella sessione
        HttpSession session = request.getSession();
        session.setAttribute("user", authentication.getPrincipal());

        return "Login effettuato con successo!";
    }

    /**
     * Logout: Invalida la sessione dell'utente.
     */
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 🔹 Invalida la sessione
        }

        SecurityContextHolder.clearContext();
        return "Logout effettuato con successo!";
    }
}
