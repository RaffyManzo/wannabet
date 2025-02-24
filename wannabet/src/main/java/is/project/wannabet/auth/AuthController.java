package is.project.wannabet.auth;

import is.project.wannabet.factory.AccountRegistratoFactory;
import is.project.wannabet.factory.ContoFactory;
import is.project.wannabet.factory.SaldoFedeltaFactory;
import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.model.Conto;
import is.project.wannabet.model.SaldoFedelta;
import is.project.wannabet.model.TipoAccount;
import is.project.wannabet.payload.LoginRequest;
import is.project.wannabet.payload.RegistrazioneRequest;
import is.project.wannabet.repository.AccountRegistratoRepository;
import is.project.wannabet.security.CustomPasswordEncoder;
import is.project.wannabet.service.AccountRegistratoService;
import is.project.wannabet.service.ContoService;
import is.project.wannabet.service.SaldoFedeltaService;
import jakarta.servlet.ServletException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AccountRegistratoService accountRegistratoService;

    @Autowired
    private ContoService contoService;

    @Autowired
    private SaldoFedeltaService saldoFedeltaService;

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registrazione di un nuovo utente.
     */
    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> register(@Valid @RequestBody RegistrazioneRequest registrazioneRequest) {

        // Controlla se l'email è già in uso
        if (accountRegistratoService.getAccountByEmail(registrazioneRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Errore: Email già in uso!");
        }

        Conto newConto = ContoFactory.createConto(registrazioneRequest.getIndirizzoDiFatturazione());
        contoService.saveConto(newConto);

        SaldoFedelta saldoFedelta = SaldoFedeltaFactory.createSaldoFedelta();
        saldoFedeltaService.saveSaldoFedelta(saldoFedelta);

        AccountRegistrato nuovoAccount = AccountRegistratoFactory.createAccountRegistrato(
                registrazioneRequest.getNome(), registrazioneRequest.getCognome(),
                registrazioneRequest.getCodiceFiscale(), newConto, registrazioneRequest.getDataDiNascita(),
                registrazioneRequest.getEmail(), registrazioneRequest.getPassword(), saldoFedelta
        );

        // Hash della password
        nuovoAccount.setPassword(passwordEncoder.encode(registrazioneRequest.getPassword()));

        // Salva nel database
        accountRegistratoService.saveAccount(nuovoAccount);

        return ResponseEntity.ok("Registrazione effettuata con successo");
    }


    /**
     * Login: Spring Security gestisce automaticamente l'autenticazione.
     * Se l'utente è autenticato, carichiamo i suoi dati nella sessione.
     */
    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        // Imposta l'autenticazione nel SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Forza la creazione della sessione e memorizza il SecurityContext sotto la chiave standard
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        System.out.println("✅ Sessione creata con ID: " + session.getId());

        successHandler.onAuthenticationSuccess(request, response, authentication);
    }



    /**
     * Logout: Invalida la sessione dell'utente.
     */
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 🔹 Invalida la sessione
        }

        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout effettuato con successo");
    }
}
