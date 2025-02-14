package is.project.wannabet.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import is.project.wannabet.model.TipoAccount;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class RegistrazioneRequest {

    @NotBlank // Valida il campo, se é vuoto 400 BAD REQUEST
    @Size(max = 50)
    @Email
    private String email;


    @NotBlank
    @Size(min = 6, max = 64)
    private String password;

    @NotBlank
    private String nome;

    @NotBlank
    private String cognome;

    @NotBlank
    @Size(min = 15, max = 16)
    private String codiceFiscale;

    @NotBlank
    @Size(max = 100)
    private String indirizzoDiFatturazione;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull
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
