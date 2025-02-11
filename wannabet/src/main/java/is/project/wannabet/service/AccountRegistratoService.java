package is.project.wannabet.service;

import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.repository.AccountRegistratoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class AccountRegistratoService implements UserDetailsService {

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

    public AccountRegistrato getAccountByEmail(String email) {
        return accountRegistratoRepository.findByEmail(email).get();
    }

    @Transactional
    public void flush() {
        accountRegistratoRepository.flush();
    }

    public void deleteAccount(Long id) {
        accountRegistratoRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRegistratoRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato con email: " + email));
    }
}

