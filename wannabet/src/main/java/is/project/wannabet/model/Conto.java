package is.project.wannabet.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "conto")
@JsonInclude(JsonInclude.Include.NON_NULL) // Esclude i campi nulli dalla serializzazione
public class Conto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conto")
    @JsonProperty("id_conto")
    private Long idConto;

    @Column(name = "saldo", nullable = false)
    @JsonProperty("saldo")
    private Double saldo;

    @Column(name = "data_creazione", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("data_creazione")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date dataCreazione;

    @Column(name = "indirizzo_fatturazione", length = 100)
    @JsonProperty("indirizzo_fatturazione")
    private String indirizzoFatturazione;


    // Getters e Setters
    public Long getIdConto() {
        return idConto;
    }

    public void setIdConto(Long idConto) {
        this.idConto = idConto;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Date getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public String getIndirizzoFatturazione() {
        return indirizzoFatturazione;
    }

    public void setIndirizzoFatturazione(String indirizzoFatturazione) {
        this.indirizzoFatturazione = indirizzoFatturazione;
    }

    public void aggiungiSaldo(double importo) {
        this.saldo += importo;
    }

    public boolean preleva(double importo) {
        if (saldo >= importo) {
            saldo -= importo;
            return true;
        }
        return false;
    }

    public void deposita(double importo) {
        saldo += importo;
    }

    public boolean verificaSaldo(double importo) {
        return saldo >= importo;
    }
}
