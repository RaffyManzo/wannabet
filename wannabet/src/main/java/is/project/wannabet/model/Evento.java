package is.project.wannabet.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEvento")
    private Long idEvento;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "data", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    @Column(name = "luogo", length = 100)
    private String luogo;

    // Relazione con le Quote (Un evento può avere più quote associate)
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quota> quote;

    // Getters e Setters
    public Long getIdEvento() { return idEvento; }
    public void setIdEvento(Long idEvento) { this.idEvento = idEvento; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public String getLuogo() { return luogo; }
    public void setLuogo(String luogo) { this.luogo = luogo; }

    public List<Quota> getQuote() { return quote; }
    public void setQuote(List<Quota> quote) { this.quote = quote; }
}
