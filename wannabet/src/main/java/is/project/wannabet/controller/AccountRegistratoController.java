package is.project.wannabet.controller;

import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.security.AuthenticationRequestAccountCheck;
import is.project.wannabet.service.AccountRegistratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountRegistratoController {

    @Autowired
    private AccountRegistratoService service;


    @Autowired
    private AuthenticationRequestAccountCheck accountCheck;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<AccountRegistrato> getAllAccounts() {
        return service.getAllAccounts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('UTENTE', 'ADMIN')")
    public Optional<AccountRegistrato> getAccountById(@PathVariable Long id, Authentication authentication) {
        ResponseEntity<?> response = accountCheck.checkAccount(id, authentication);

        if (response.getStatusCode() == HttpStatus.OK ||
                authentication.getAuthorities().stream()
                        .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))
        ) {
            return service.getAccountById(id);
        } else
            return Optional.empty();
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('UTENTE', 'ADMIN')")
    public void deleteAccount(@PathVariable Long id, Authentication authentication) {
        ResponseEntity<?> response = accountCheck.checkAccount(id, authentication);

        if (response.getStatusCode() == HttpStatus.OK ||
                authentication.getAuthorities().stream()
                        .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))
        ) {
            service.deleteAccount(id);
        }
    }
}
