package is.project.wannabet.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "quota")
public class Quota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idquota")
    private Long idQuota;

    @Column(name = "descrizione", nullable = false, length = 100)
    private String descrizione;

    @Column(name = "valore", nullable = false)
    private BigDecimal valore;

    // Relazione con Eventi (ipotizzando che ogni quota sia collegata a un evento)
    @ManyToOne
    @JoinColumn(name = "idEvento", nullable = false)
    private Evento evento;

    // Getters e Setters
    public Long getIdQuota() { return idQuota; }
    public void setIdQuota(Long idQuota) { this.idQuota = idQuota; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public BigDecimal getValore() { return valore; }
    public void setValore(BigDecimal valore) { this.valore = valore; }

    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }
}
