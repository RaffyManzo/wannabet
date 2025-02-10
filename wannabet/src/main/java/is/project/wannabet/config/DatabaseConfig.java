package is.project.wannabet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

/**
 * Configurazione del database dell'applicazione.
 * Definisce la connessione al database MySQL tramite un bean di tipo {@link DataSource}.
 */
@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String dbDriver;

    /**
     * Configura la connessione al database MySQL.
     * Recupera le credenziali e i dettagli di connessione dai file di configurazione di Spring.
     *
     * @return Un oggetto {@link DataSource} configurato per la connessione al database.
     */
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName(dbDriver)
                .url(dbUrl)
                .username(dbUsername)
                .password(dbPassword)
                .build();
    }
}
