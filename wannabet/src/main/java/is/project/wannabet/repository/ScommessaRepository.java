package is.project.wannabet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import is.project.wannabet.model.Scommessa;

import java.util.List;

public interface ScommessaRepository extends JpaRepository<Scommessa, Long> {
    List<Scommessa> findByAccountId(Long accountId); // Trova tutte le scommesse di un utente
}

