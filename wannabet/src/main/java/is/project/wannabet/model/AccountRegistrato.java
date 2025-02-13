package is.project.wannabet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import is.project.wannabet.util.TipoAccountConverter;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "account_registrato")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountRegistrato  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_account")
    @JsonProperty("id_account")
    private Long idAccount;

    @Column(name = "codice_fiscale", nullable = false, length = 45, unique = true)
    @JsonProperty("codice_fiscale")
    private String codiceFiscale;

    @Convert(converter = TipoAccountConverter.class)
    @Column(name = "tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    @JsonProperty("tipo")
    private TipoAccount tipo;

    @ManyToOne
    @JoinColumn(name = "id_conto", nullable = false)
    @JsonProperty("conto")
    private Conto conto;

    @ManyToOne
    @JoinColumn(name = "id_fedelta", nullable = false)
    @JsonProperty("saldo_fedelta")
    private SaldoFedelta saldoFedelta;

    @Column(name = "nome", length = 45)
    @JsonProperty("nome")
    private String nome;

    @Column(name = "cognome", length = 45)
    @JsonProperty("cognome")
    private String cognome;

    @Column(name = "email", length = 100, unique = true, nullable = false)
    @JsonProperty("email")
    private String email;

    @Column(name = "password", nullable = false)
    @JsonProperty("password")
    @JsonIgnore
    private String password;

    @Column(name = "data_nascita")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    @JsonProperty("data_nascita")
    private Date dataNascita;


    // Getters e Setters


    public String getPassword() {
        return password;
    }

    public Long getIdAccount() { return idAccount; }
    public void setIdAccount(Long idAccount) { this.idAccount = idAccount; }

    public String getCodiceFiscale() { return codiceFiscale; }
    public void setCodiceFiscale(String codiceFiscale) { this.codiceFiscale = codiceFiscale; }

    public TipoAccount getTipo() { return tipo; }
    public void setTipo(TipoAccount tipo) { this.tipo = tipo; }

    public Conto getConto() { return conto; }
    public void setConto(Conto conto) { this.conto = conto; }

    public SaldoFedelta getSaldoFedelta() { return saldoFedelta; }
    public void setSaldoFedelta(SaldoFedelta saldoFedelta) { this.saldoFedelta = saldoFedelta; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public Date getDataNascita() { return dataNascita; }
    public void setDataNascita(Date dataNascita) { this.dataNascita = dataNascita; }
}
