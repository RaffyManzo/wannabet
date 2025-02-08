package is.project.wannabet.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "quota")
public class Quota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idquota")
    private Long idQuota;

    @Column(name = "descrizione", nullable = false, length = 100)
    private String descrizione;

    @Column(name = "categoria", nullable = false, length = 45)
    private String categoria;

    @Column(name = "moltipicatore", nullable = false)
    private double moltipicatore;

    @ManyToOne
    @JoinColumn(name = "idEvento", nullable = false)
    private Evento evento;

    @ManyToMany(mappedBy = "quote") // Relazione bidirezionale con Scommessa
    private List<Scommessa> scommesse;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato", nullable = false)
    private StatoQuota stato;

    // Getters e Setters
    public Long getIdQuota() {
        return idQuota;
    }



    public void setIdQuota(Long idQuota) {
        this.idQuota = idQuota;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }


    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    public double getMoltipicatore() {
        return moltipicatore;
    }

    public void setMoltipicatore(double moltipicatore) {
        this.moltipicatore = moltipicatore;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public List<Scommessa> getScommesse() {
        return scommesse;
    }

    public void setScommesse(List<Scommessa> scommesse) {
        this.scommesse = scommesse;
    }

    public StatoQuota getStato() {
        return stato;
    }

    public void setStato(StatoQuota stato) {
        this.stato = stato;
    }
}
