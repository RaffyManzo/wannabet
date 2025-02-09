package is.project.wannabet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "quota_giocata")
public class QuotaGiocata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_link")
    private Long idLink;

    @ManyToOne
    @JoinColumn(name = "idScommessa", nullable = false)
    @JsonBackReference
    private Scommessa scommessa;


    @ManyToOne
    @JoinColumn(name = "id_quota", nullable = false)
    private Quota quota;

    @Column(name = "moltiplicatore", nullable = false)
    private double moltiplicatore;

    public QuotaGiocata() {}

    public QuotaGiocata(Scommessa scommessa, Quota quota) {
        this.scommessa = scommessa;
        this.quota = quota;
        this.moltiplicatore = quota.getMoltipicatore();
    }

    public Long getIdLink() { return idLink; }
    public Scommessa getScommessa() { return scommessa; }
    public Quota getQuota() { return quota; }
    public double getMoltiplicatore() { return moltiplicatore; }

    public void setScommessa(Scommessa scommessa) { this.scommessa = scommessa; }
    public void setQuota(Quota quota) { this.quota = quota; }
    public void setMoltiplicatore(double moltiplicatoreCongelato) { this.moltiplicatore = moltiplicatoreCongelato; }
}
