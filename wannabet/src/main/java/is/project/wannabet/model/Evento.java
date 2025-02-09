package is.project.wannabet.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "evento")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    @JsonProperty("id_evento")
    private Long idEvento;

    @Column(name = "nome", nullable = false, length = 100)
    @JsonProperty("nome")
    private String nome;

    @Column(name = "data_evento", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("data_evento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date data;

    @Column(name = "descrizione", length = 100)
    @JsonProperty("descrizione")
    private String descrizione;

    @Column(name = "chiuso", nullable = false)
    @JsonProperty("chiuso")
    private boolean chiuso = false;

    @Column(name = "categoria", length = 45)
    @JsonProperty("categoria")
    private String categoria;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @JsonProperty("quote")
    private List<Quota> quote;

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public boolean isChiuso() {
        return chiuso;
    }

    public void setChiuso(boolean chiuso) {
        this.chiuso = chiuso;
    }

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
