package is.project.wannabet.model;

import jakarta.persistence.*;

@Entity
@Table(name = "saldo_fedelta")
public class SaldoFedelta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_saldo_fedelta")
    private Long idSaldoFedelta;

    @Column(name = "punti", nullable = false)
    private Integer punti;

    // Getters e Setters
    public Long getIdSaldoFedelta() { return idSaldoFedelta; }
    public void setIdSaldoFedelta(Long idSaldoFedelta) { this.idSaldoFedelta = idSaldoFedelta; }

    public Integer getPunti() { return punti; }
    public void setPunti(Integer punti) { this.punti = punti; }
}
