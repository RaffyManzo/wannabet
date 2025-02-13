package is.project.wannabet.service;

import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.model.AccountRegistratoDetails;
import is.project.wannabet.repository.AccountRegistratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsService implements UserDetailsService {

    @Autowired
    private AccountRegistratoRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AccountRegistrato account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato con email: " + email));

        return new AccountRegistratoDetails(account);
    }
}

