package is.project.wannabet.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "conto")
public class Conto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idconto")
    private Long idConto;

    @Column(name = "saldo", nullable = false)
    private Double saldo;

    @Column(name = "data_crazione", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCreazione;

    @Column(name = "indirizzo_fatturazione", length = 100)
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
}
