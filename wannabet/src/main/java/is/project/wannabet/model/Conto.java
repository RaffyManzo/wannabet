package is.project.wannabet.model;

import jakarta.persistence.*;

@Entity
@Table(name = "conto")
public class Conto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idconto")
    private Long idConto;

    @Column(name = "saldo", nullable = false)
    private Double saldo;

    // Getters e Setters
    public Long getIdConto() { return idConto; }
    public void setIdConto(Long idConto) { this.idConto = idConto; }

    public Double getSaldo() { return saldo; }
    public void setSaldo(Double saldo) { this.saldo = saldo; }
}
