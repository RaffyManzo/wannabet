package is.project.wannabet.controller;

import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.service.AccountRegistratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountRegistratoController {

    @Autowired
    private AccountRegistratoService service;

    @GetMapping
    public List<AccountRegistrato> getAllAccounts() {
        return service.getAllAccounts();
    }

    @GetMapping("/{id}")
    public Optional<AccountRegistrato> getAccountById(@PathVariable Long id) {
        return service.getAccountById(id);
    }

    @PostMapping
    public AccountRegistrato createAccount(@RequestBody AccountRegistrato account) {
        return service.saveAccount(account);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        service.deleteAccount(id);
    }
}
