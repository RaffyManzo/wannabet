package is.project.wannabet.model;

import com.fasterxml.jackson.annotation.*;
import is.project.wannabet.util.StatoScommessaConverter;
import is.project.wannabet.util.TipoScommessaConverter;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "scommessa")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_scommessa")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Scommessa {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_scommessa")
    @JsonProperty("id_scommessa")
    private Long idScommessa;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_account")
    @ManyToOne
    @JoinColumn(name = "id_account", nullable = false)
    private AccountRegistrato account;

    @Column(name = "tipo", nullable = false)
    @Convert(converter = TipoScommessaConverter.class)
    @Enumerated(EnumType.STRING)
    @JsonProperty("tipo")
    private TipoScommessa tipo;

    @Column(name = "importo", nullable = false)
    @JsonProperty("importo")
    private double importo;

    @Column(name = "vincita", nullable = false)
    @JsonProperty("vincita")
    private double vincita;

    @Column(name = "data_scommessa", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("data_scommessa")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date data;

    @Convert(converter = StatoScommessaConverter.class)
    @Column(name = "stato", nullable = false)
    @JsonProperty("stato")
    private StatoScommessa stato;

    @OneToMany(mappedBy = "scommessa", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonProperty("quote_giocate")
    private List<QuotaGiocata> quoteGiocate = new ArrayList<>();

    @OneToOne(mappedBy = "scommessa")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Prenotazione prenotazione;


    public Prenotazione getPrenotazione() {
        return prenotazione;
    }

    public void setPrenotazione(Prenotazione prenotazione) {
        this.prenotazione = prenotazione;
    }


    public List<QuotaGiocata> getQuoteGiocate() {
        return quoteGiocate;
    }

    public void setQuoteGiocate(List<QuotaGiocata> quoteGiocate) {
        this.quoteGiocate.clear(); // Svuota la lista mantenendo il riferimento
        if (quoteGiocate != null) {
            this.quoteGiocate.addAll(quoteGiocate); // Aggiungi i nuovi elementi
        }
    }

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

    public TipoScommessa getTipo() {
        return tipo;
    }

    public void setTipo(TipoScommessa tipo) {
        this.tipo = tipo;
    }
}
