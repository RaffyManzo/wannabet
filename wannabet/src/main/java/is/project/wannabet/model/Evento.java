package is.project.wannabet.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Long idEvento;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "data_evento", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    @Column(name = "descrizione", length = 100)
    private String descrizione;

    @Column(name = "chiuso", nullable = false)
    private boolean chiuso = false;

    @Column(name = "categoria", length = 45)
    private String categoria;

    // Relazione con le Quote (Un evento può avere più quote associate)
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quota> quote;

    // Getters e Setters


    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public boolean isChiuso() { return chiuso; }
    public void setChiuso(boolean chiuso) { this.chiuso = chiuso; }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
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

    public List<Quota> getQuote() {
        return quote;
    }

    public void setQuote(List<Quota> quote) {
        this.quote = quote;
    }
}
