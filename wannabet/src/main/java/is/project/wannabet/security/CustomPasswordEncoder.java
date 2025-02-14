package is.project.wannabet.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class CustomPasswordEncoder implements PasswordEncoder {

    /**
     * Genera un hash SHA-256 della password fornita.
     *
     * @param password La password da hashare.
     * @return L'hash SHA-256 codificato in Base64.
     */
    public static String sha256(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Errore durante la generazione dell'hash SHA-256", e);
        }
    }

    /**
     * Confronta una password con un hash SHA-256 esistente.
     *
     * @param password La password da verificare.
     * @param hashedPassword L'hash SHA-256 salvato (Base64).
     * @return true se la password corrisponde all'hash, false altrimenti.
     */
    public static boolean verifyPassword(String password, String hashedPassword) {
        return sha256(password).equals(hashedPassword);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return sha256(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
