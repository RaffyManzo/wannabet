package is.project.wannabet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.CookieSerializer;

@Configuration
public class CookieConfig {

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("JSESSIONID"); // Nome del cookie
        serializer.setSameSite("Lax"); // Opzione SameSite
        serializer.setUseSecureCookie(false); // Deve essere true in produzione con HTTPS
        serializer.setUseHttpOnlyCookie(true);
        return serializer;
    }
}
