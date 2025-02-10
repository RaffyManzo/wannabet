package is.project.wannabet.repository;

import is.project.wannabet.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository per l'entità Evento.
 * Fornisce metodi per accedere agli eventi nel database.
 */
@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByNomeContainingIgnoreCase(String nome); // Ricerca eventi per nome
}
