package is.project.wannabet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import is.project.wannabet.model.StoricoRiscossioni;

import java.util.List;

public interface StoricoRiscossioniRepository extends JpaRepository<StoricoRiscossioni, Long> {
    List<StoricoRiscossioni> findBySaldoFedeltaId(Long saldoFedeltaId);
}
