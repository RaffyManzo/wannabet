package is.project.wannabet.model;

import jakarta.persistence.*;

@Entity
@Table(name = "quota_giocata")
public class QuotaGiocata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLink")
    private Long idLink;

    @ManyToOne
    @JoinColumn(name = "idScommessa", nullable = false)
    private Scommessa scommessa;

    @ManyToOne
    @JoinColumn(name = "idQuota", nullable = false)
    private Quota quota;

    @Column(name = "moltiplicatore_congelato", nullable = false)
    private double moltiplicatoreCongelato;

    public QuotaGiocata() {}

    public QuotaGiocata(Scommessa scommessa, Quota quota) {
        this.scommessa = scommessa;
        this.quota = quota;
        this.moltiplicatoreCongelato = quota.getMoltipicatore();
    }

    public Long getIdLink() { return idLink; }
    public Scommessa getScommessa() { return scommessa; }
    public Quota getQuota() { return quota; }
    public double getMoltiplicatoreCongelato() { return moltiplicatoreCongelato; }

    public void setScommessa(Scommessa scommessa) { this.scommessa = scommessa; }
    public void setQuota(Quota quota) { this.quota = quota; }
    public void setMoltiplicatoreCongelato(double moltiplicatoreCongelato) { this.moltiplicatoreCongelato = moltiplicatoreCongelato; }
}
