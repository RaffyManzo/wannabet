package is.project.wannabet.model;

import jakarta.persistence.*;

@Entity
@Table(name = "premi_fedelta")
public class PremiFedelta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPremio")
    private Long idPremio;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "descrizione", length = 255)
    private String descrizione;

    @Column(name = "punti_richiesti", nullable = false)
    private Integer puntiRichiesti;

    // Getters e Setters
    public Long getIdPremio() { return idPremio; }
    public void setIdPremio(Long idPremio) { this.idPremio = idPremio; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public Integer getPuntiRichiesti() { return puntiRichiesti; }
    public void setPuntiRichiesti(Integer puntiRichiesti) { this.puntiRichiesti = puntiRichiesti; }
}
