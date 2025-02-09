package is.project.wannabet.repository;

import is.project.wannabet.model.Conto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContoRepository extends JpaRepository<Conto, Long> {
    Optional<Conto> findByAccount_IdAccount(Long idAccount);
}
