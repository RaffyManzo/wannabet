package is.project.wannabet.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import is.project.wannabet.model.TipoAccount;
import jakarta.validation.constraints.*;

import java.util.Date;

public class RegistrazioneRequest {

    @NotBlank(message = "L'email è obbligatoria")
    @Size(max = 50, message = "L'email non può superare i 50 caratteri")
    @Email(message = "L'email non è valida")
    private String email;

    @NotBlank(message = "La password è obbligatoria")
    @Size(min = 6, max = 64, message = "La password deve essere compresa tra 6 e 64 caratteri")
    private String password;

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(max = 100, message = "Il nome non può superare i 100 caratteri")
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "Il nome non può contenere numeri o caratteri speciali")
    private String nome;

    @NotBlank(message = "Il cognome è obbligatorio")
    @Size(max = 100, message = "Il cognome non può superare i 100 caratteri")
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "Il cognome non può contenere numeri o caratteri speciali")
    private String cognome;

    @NotBlank(message = "Il codice fiscale è obbligatorio")
    @Size(min = 15, max = 16, message = "Il codice fiscale deve essere lungo 15 o 16 caratteri")
    @Pattern(regexp = "^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$", message = "Il codice fiscale non è valido")
    private String codiceFiscale;

    @NotBlank(message = "L'indirizzo di fatturazione è obbligatorio")
    @Size(max = 100, message = "L'indirizzo di fatturazione non può superare i 100 caratteri")
    @Pattern(regexp = ".*\\d+.*", message = "L'indirizzo di fatturazione deve contenere almeno un numero")
    private String indirizzoDiFatturazione;

    @NotNull(message = "La data di nascita è obbligatoria")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataDiNascita;


    public String getIndirizzoDiFatturazione() {
        return indirizzoDiFatturazione;
    }

    public void setIndirizzoDiFatturazione(String indirizzoDiFatturazione) {
        this.indirizzoDiFatturazione = indirizzoDiFatturazione;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public Date getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(Date dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }
}
