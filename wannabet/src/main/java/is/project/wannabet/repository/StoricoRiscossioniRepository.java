package is.project.wannabet.repository;


import is.project.wannabet.model.StoricoRiscossioni;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface StoricoRiscossioniRepository extends JpaRepository<StoricoRiscossioni, Long> {
    List<StoricoRiscossioni> findBySaldoFedelta_IdSaldoFedelta(Long saldoFedeltaId);
}
