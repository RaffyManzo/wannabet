package is.project.wannabet.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "quota")
public class Quota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Quota")
    private Long idQuota;

    @Column(name = "moltiplicatore", nullable = false)
    private double moltipicatore;

    @Column(name = "descrizione", nullable = false, length = 100)
    private String descrizione;

    @Column(name = "categoria", nullable = false, length = 100)
    private String categoria;

    @ManyToOne
    @JoinColumn(name = "idEvento", nullable = false)
    private Evento evento;

    @Enumerated(EnumType.STRING)
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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
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
