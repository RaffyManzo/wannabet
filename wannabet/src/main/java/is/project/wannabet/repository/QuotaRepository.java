package is.project.wannabet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import is.project.wannabet.model.Quota;

import java.util.List;

public interface QuotaRepository extends JpaRepository<Quota, Long> {
    List<Quota> findByEventoId(Long eventoId); // Trova tutte le quote associate a un evento
}
