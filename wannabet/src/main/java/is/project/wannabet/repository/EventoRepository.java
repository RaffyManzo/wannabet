package is.project.wannabet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import is.project.wannabet.model.Evento;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByNomeContainingIgnoreCase(String nome); // Ricerca eventi per nome
}
