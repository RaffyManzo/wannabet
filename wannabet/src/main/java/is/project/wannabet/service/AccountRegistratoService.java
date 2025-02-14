package is.project.wannabet.service;

import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.model.Conto;
import is.project.wannabet.repository.AccountRegistratoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class AccountRegistratoService {

    @Autowired
    private AccountRegistratoRepository accountRegistratoRepository;

    public List<AccountRegistrato> getAllAccounts() {
        return accountRegistratoRepository.findAll();
    }

    public Optional<AccountRegistrato> getAccountById(Long id) {
        return accountRegistratoRepository.findById(id);
    }

    public AccountRegistrato saveAccount(AccountRegistrato account) {
        return accountRegistratoRepository.save(account);
    }

    public AccountRegistrato getAccountByCodiceFiscale(String codiceFicale) {
        return accountRegistratoRepository.findByCodiceFiscale(codiceFicale).get();
    }

    public Optional<AccountRegistrato> getAccountByEmail(String email) {
        return accountRegistratoRepository.findByEmail(email);
    }

    @Transactional
    public void flush() {
        accountRegistratoRepository.flush();
    }

    public void deleteAccount(Long id) {
        accountRegistratoRepository.deleteById(id);
    }


}

