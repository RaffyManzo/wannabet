package is.project.wannabet.repository;

import is.project.wannabet.model.StoricoRiscossioni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository per l'entità StoricoRiscossioni.
 * Gestisce l'accesso ai dati relativi alla cronologia dei riscatti dei premi fedeltà.
 */
@Repository
public interface StoricoRiscossioniRepository extends JpaRepository<StoricoRiscossioni, Long> {
    List<StoricoRiscossioni> findBySaldoFedelta_IdSaldoFedelta(Long saldoFedeltaId);
}
