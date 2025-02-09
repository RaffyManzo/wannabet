package is.project.wannabet.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "scommessa")
public class Scommessa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idScommessa")
    private Long idScommessa;

    @ManyToOne
    @JoinColumn(name = "idAccount", nullable = false)
    private AccountRegistrato account;

    @Column(name = "importo", nullable = false)
    private double importo;

    @Column(name = "vincita", nullable = false)
    private double vincita;

    @Column(name = "data_scommessa", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato", nullable = false)
    private StatoScommessa stato;

    // Relazione ManyToMany con Quota tramite una tabella intermedia
    @ManyToMany
    @JoinTable(
            name = "lista_quote",  // Nome della tabella intermedia
            joinColumns = @JoinColumn(name = "idScommessa"),
            inverseJoinColumns = @JoinColumn(name = "idQuota")
    )
    private List<Quota> quote;

    // Getters e Setters
    public Long getIdScommessa() { return idScommessa; }
    public void setIdScommessa(Long idScommessa) { this.idScommessa = idScommessa; }

    public AccountRegistrato getAccount() { return account; }
    public void setAccount(AccountRegistrato account) { this.account = account; }

    public double getImporto() { return importo; }
    public void setImporto(double importo) { this.importo = importo; }

    public double getVincita() {
        return vincita;
    }

    public void setVincita(double vincita) {
        this.vincita = vincita;
    }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public StatoScommessa getStato() { return stato; }
    public void setStato(StatoScommessa stato) { this.stato = stato; }

    public List<Quota> getQuote() { return quote; }
    public void setQuote(List<Quota> quote) { this.quote = quote; }
}
