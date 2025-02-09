package is.project.wannabet.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "account_registrato")
public class AccountRegistrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAccount")
    private Long idAccount;

    @Column(name = "Codice_Fiscale", nullable = false, length = 45, unique = true)
    private String codiceFiscale;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoAccount tipo;

    @ManyToOne
    @JoinColumn(name = "idConto", nullable = false)
    private Conto conto;

    @ManyToOne
    @JoinColumn(name = "idFedelta", nullable = false)
    private SaldoFedelta saldoFedelta;

    @Column(name = "nome", length = 45)
    private String nome;

    @Column(name = "cognome", length = 45)
    private String cognome;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "data_nascita")
    private Date dataNascita;

    // Getters e Setters
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

    public Date getDataNascita() { return dataNascita; }
    public void setDataNascita(Date dataNascita) { this.dataNascita = dataNascita; }
}