package is.project.wannabet.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class PasswordEncoding {

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
}
