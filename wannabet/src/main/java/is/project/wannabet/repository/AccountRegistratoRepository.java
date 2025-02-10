package is.project.wannabet.repository;

import is.project.wannabet.model.AccountRegistrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRegistratoRepository extends JpaRepository<AccountRegistrato, Long> {
}

