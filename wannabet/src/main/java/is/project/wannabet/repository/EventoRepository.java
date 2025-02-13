package is.project.wannabet.repository;

import is.project.wannabet.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository per l'entità Evento.
 * Fornisce metodi per accedere agli eventi nel database.
 */
@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByNomeContainingIgnoreCase(String nome); // Ricerca eventi per nome

    @Modifying
    @Query("UPDATE Evento e SET e.nome = :nome, e.descrizione = :descrizione, e.categoria = :categoria, e.chiuso = :chiuso WHERE e.idEvento = :idEvento")
    void updateEvento(@Param("idEvento") Long idEvento, @Param("nome") String nome,
                      @Param("descrizione") String descrizione, @Param("categoria") String categoria,
                      @Param("chiuso") boolean chiuso);

    List<Evento> findEventoByCategoria(String categoria);

    List<Evento> findEventoByCategoriaOrderByDataAsc(String categoria);

    List<Evento> findEventoByCategoriaAndDescrizione(String categoria, String descrizione);

}
