package is.project.wannabet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configurazione della sicurezza dell'applicazione tramite Spring Security.
 * Questa classe definisce le regole di accesso alle API e la gestione della protezione CSRF.
 */

// TODO: Implementar l'effettiva sicurezza disabilitata per testing
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Definisce la catena di filtri di sicurezza per l'applicazione.
     * - Disabilita la protezione CSRF (Cross-Site Request Forgery).
     * - Permette a tutte le richieste di accedere senza autenticazione.
     *
     * @param http Oggetto {@link HttpSecurity} per configurare la sicurezza.
     * @return Una istanza di {@link SecurityFilterChain} con le configurazioni applicate.
     * @throws Exception Eccezione generata in caso di errore nella configurazione.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }
}
