package is.project.wannabet.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "storico_riscossioni")
public class StoricoRiscossioni {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_riscossione")
    private Long idRiscossione;

    @ManyToOne
    @JoinColumn(name = "idSaldoFedelta", nullable = false)
    private SaldoFedelta saldoFedelta;

    @ManyToOne
    @JoinColumn(name = "idPremio", nullable = false)
    private PremiFedelta premio;

    @Column(name = "data_riscossione", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRiscossione;

    // Costruttori
    public StoricoRiscossioni() {}

    public StoricoRiscossioni(SaldoFedelta saldoFedelta, PremiFedelta premio, Date dataRiscossione) {
        this.saldoFedelta = saldoFedelta;
        this.premio = premio;
        this.dataRiscossione = dataRiscossione;
    }

    // Getters e Setters
    public Long getIdRiscossione() { return idRiscossione; }
    public void setIdRiscossione(Long idRiscossione) { this.idRiscossione = idRiscossione; }

    public SaldoFedelta getSaldoFedelta() { return saldoFedelta; }
    public void setSaldoFedelta(SaldoFedelta saldoFedelta) { this.saldoFedelta = saldoFedelta; }

    public PremiFedelta getPremio() { return premio; }
    public void setPremio(PremiFedelta premio) { this.premio = premio; }

    public Date getDataRiscossione() { return dataRiscossione; }
    public void setDataRiscossione(Date dataRiscossione) { this.dataRiscossione = dataRiscossione; }
}