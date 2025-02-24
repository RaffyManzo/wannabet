package is.project.wannabet.auth;

import is.project.wannabet.service.QuotaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));

        authentication.getAuthorities().forEach(auth ->
                System.out.println("➡️ Ruolo trovato: " + auth.getAuthority())
        );

        boolean isGestoreReferti = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_GESTORE_REFERTI"));

        String redirectUrl = isGestoreReferti ? "/referta-quote" : "/home";

        System.out.println("È una richiesta AJAX? " + isAjax);
        System.out.println("Redirezione impostata su: " + redirectUrl);

        if (isAjax) {
            // 🔹 Se è una chiamata AJAX, restituisce solo JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"redirectUrl\": \"" + redirectUrl + "\"}");
        } else {
            // 🔹 Se è una richiesta normale, fa il redirect
            response.sendRedirect(request.getContextPath() + redirectUrl);
        }
    }



}
