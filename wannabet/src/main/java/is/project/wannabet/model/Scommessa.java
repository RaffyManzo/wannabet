package is.project.wannabet.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import is.project.wannabet.util.StatoScommessaConverter;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "scommessa")
public class Scommessa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_scommessa")
    private Long idScommessa;

    @ManyToOne
    @JoinColumn(name = "id_account", nullable = false)
    private AccountRegistrato account;

    @Column(name = "importo", nullable = false)
    private double importo;

    @Column(name = "vincita", nullable = false)
    private double vincita;

    @Column(name = "data_scommessa", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    @Convert(converter = StatoScommessaConverter.class)
    @Column(name = "stato", nullable = false)
    private StatoScommessa stato;

    @OneToMany(mappedBy = "scommessa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<QuotaGiocata> quoteGiocate = new ArrayList<>();

    public List<QuotaGiocata> getQuoteGiocate() { return quoteGiocate; }
    public void setQuoteGiocate(List<QuotaGiocata> quoteGiocate) { this.quoteGiocate = quoteGiocate; }

    public Long getIdScommessa() {
        return idScommessa;
    }

    public void setIdScommessa(Long idScommessa) {
        this.idScommessa = idScommessa;
    }

    public AccountRegistrato getAccount() {
        return account;
    }

    public void setAccount(AccountRegistrato account) {
        this.account = account;
    }

    public double getImporto() {
        return importo;
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }

    public double getVincita() {
        return vincita;
    }

    public void setVincita(double vincita) {
        this.vincita = vincita;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public StatoScommessa getStato() {
        return stato;
    }

    public void setStato(StatoScommessa stato) {
        this.stato = stato;
    }
}
