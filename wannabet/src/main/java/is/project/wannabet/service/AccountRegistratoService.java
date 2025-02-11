package is.project.wannabet.service;

import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.repository.AccountRegistratoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class AccountRegistratoService {

    @Autowired
    private AccountRegistratoRepository repository;

    public List<AccountRegistrato> getAllAccounts() {
        return repository.findAll();
    }

    public Optional<AccountRegistrato> getAccountById(Long id) {
        return repository.findById(id);
    }

    public AccountRegistrato saveAccount(AccountRegistrato account) {
        return repository.save(account);
    }

    @Transactional
    public void flush() {
        repository.flush();
    }

    public void deleteAccount(Long id) {
        repository.deleteById(id);
    }
}

