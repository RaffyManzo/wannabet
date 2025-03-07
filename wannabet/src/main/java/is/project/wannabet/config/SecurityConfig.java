package is.project.wannabet.config;

import is.project.wannabet.auth.CustomAuthenticationSuccessHandler;
import is.project.wannabet.security.CustomPasswordEncoder;
import is.project.wannabet.service.AccountDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private AccountDetailsService accountDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   CustomAuthenticationSuccessHandler successHandler) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disabilita CSRF per le API REST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/css/**", "/script/**", "/images/**", "/login.html", "/signup.html").permitAll()

                        .anyRequest().authenticated() // 🔒 Tutto il resto richiede autenticazione
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .sessionFixation().migrateSession()  // Migra la sessione dopo l'autenticazione
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                )

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            // Reindirizza alla pagina di login per le richieste non autorizzate
                            response.sendRedirect(request.getContextPath() + "/login.html");
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout") // 🔑 Logout via API
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK); //Forza il 200 OK invece di 302
                        })
                );

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) throws Exception {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authProvider);
    }

    @Bean
    public UserDetailsService userDetailsService(AccountDetailsService accountDetailsService) {
        return accountDetailsService; // Usa il tuo AccountDetailsService
    }
}
