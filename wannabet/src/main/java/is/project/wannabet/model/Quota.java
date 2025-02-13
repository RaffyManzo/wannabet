package is.project.wannabet.model;

import com.fasterxml.jackson.annotation.*;
import is.project.wannabet.util.StatoQuotaConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "quota")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Quota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_quota")
    @JsonProperty("id_quota")
    private Long idQuota;

    @Column(name = "moltiplicatore", nullable = false)
    @JsonProperty("moltiplicatore")
    private double moltiplicatore;

    @Column(name = "esito", nullable = false, length = 100)
    @JsonProperty("esito")
    private String esito;

    @Column(name = "categoria", nullable = false, length = 100)
    @JsonProperty("categoria")
    private String categoria;


    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;



    @Convert(converter = StatoQuotaConverter.class)
    @Column(name = "stato", nullable = false)
    @JsonProperty("stato")
    private StatoQuota stato;

    @Column(name = "chiusa", nullable = false)
    @JsonProperty("chiusa")
    private boolean chiusa = false;

    public Long getIdQuota() {
        return idQuota;
    }

    public void setIdQuota(Long idQuota) {
        this.idQuota = idQuota;
    }

    public double getMoltiplicatore() {
        return moltiplicatore;
    }

    public void setMoltiplicatore(double moltiplicatore) {
        this.moltiplicatore = moltiplicatore;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Quota quota = (Quota) obj;
        return idQuota != null && idQuota.equals(quota.idQuota);
    }

    @Override
    public int hashCode() {
        return idQuota != null ? idQuota.hashCode() : 0;
    }

}
