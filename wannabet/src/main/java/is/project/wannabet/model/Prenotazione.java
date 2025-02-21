package is.project.wannabet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "prenotazione")
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prenotazione")
    private int id_prenotazione;

    @Column(name = "codice", length = 5, nullable = false)
    private String codice;

    @OneToOne
    @JoinColumn(name = "id_scommessa", nullable = false, unique = true)
    private Scommessa scommessa;

    public Prenotazione() {}

    public Prenotazione(String codice, Scommessa scommessa) {
        this.codice = codice;
        this.scommessa = scommessa;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public Scommessa getScommessa() {
        return scommessa;
    }

    public void setScommessa(Scommessa scommessa) {
        this.scommessa = scommessa;
    }

    public int getId_prenotazione() {
        return id_prenotazione;
    }

    public void setId_prenotazione(int id_prenotazione) {
        this.id_prenotazione = id_prenotazione;
    }
}
