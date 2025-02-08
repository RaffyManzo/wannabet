package is.project.wannabet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import is.project.wannabet.model.Conto;

public interface ContoRepository extends JpaRepository<Conto, Long> {
}

