package is.project.wannabet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import is.project.wannabet.model.Evento;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByNomeContainingIgnoreCase(String nome); // Ricerca eventi per nome
}
