package is.project.wannabet.controller;

import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.service.AccountRegistratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountRegistratoController {

    @Autowired
    private AccountRegistratoService service;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<AccountRegistrato> getAllAccounts() {
        return service.getAllAccounts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Optional<AccountRegistrato> getAccountById(@PathVariable Long id) {
        return service.getAccountById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public AccountRegistrato createAccount(@RequestBody AccountRegistrato account) {
        return service.saveAccount(account);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public void deleteAccount(@PathVariable Long id) {
        service.deleteAccount(id);
    }
}
