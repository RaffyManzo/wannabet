package is.project.wannabet.model;

import is.project.wannabet.util.StatoQuotaConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "quota")
public class Quota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_quota")
    private Long idQuota;

    @Column(name = "moltiplicatore", nullable = false)
    private double moltipicatore;

    @Column(name = "esito", nullable = false, length = 100)
    private String esito;

    @Column(name = "categoria", nullable = false, length = 100)
    private String categoria;

    @ManyToOne
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    @Convert(converter = StatoQuotaConverter.class)
    @Column(name = "stato", nullable = false)
    private StatoQuota stato;

    @Column(name = "chiusa", nullable = false)
    private boolean chiusa = false;

    public Long getIdQuota() {
        return idQuota;
    }

    public void setIdQuota(Long idQuota) {
        this.idQuota = idQuota;
    }

    public double getMoltipicatore() {
        return moltipicatore;
    }

    public void setMoltipicatore(double moltipicatore) {
        this.moltipicatore = moltipicatore;
    }

    public String getEsito() {
        return esito;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public StatoQuota getStato() {
        return stato;
    }

    public void setStato(StatoQuota stato) {
        this.stato = stato;
    }

    public boolean isChiusa() {
        return chiusa;
    }

    public void setChiusa(boolean chiusa) {
        this.chiusa = chiusa;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
