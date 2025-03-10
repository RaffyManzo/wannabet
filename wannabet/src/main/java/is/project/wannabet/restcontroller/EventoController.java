package is.project.wannabet.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import is.project.wannabet.model.Evento;
import is.project.wannabet.service.EventoService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/evento")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    /**
     * API per trovare eventi per categoria.
     */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Evento>> getEventoByCategoria(@PathVariable String categoria) {
        List<Evento> eventi = eventoService.findEventoByCategoria(categoria);
        return ResponseEntity.ok(eventi);
    }

    /**
     * API per trovare eventi per categoria ordinati per data.
     */
    @GetMapping("/categoria/{categoria}/order-by-data")
    public ResponseEntity<List<Evento>> getEventoByCategoriaOrderByDataAsc(@PathVariable String categoria) {
        List<Evento> eventi = eventoService.findEventoByCategoriaOrderByDataAsc(categoria);
        return ResponseEntity.ok(eventi);
    }

    /**
     * API per trovare eventi per categoria e descrizione.
     */
    @GetMapping("/categoria/{categoria}/descrizione/{descrizione}")
    public ResponseEntity<List<Evento>> getEventoByCategoriaAndDescrizione(@PathVariable String categoria, @PathVariable String descrizione) {
        List<Evento> eventi = eventoService.findEventoByCategoriaAndDescrizione(categoria, descrizione);
        return ResponseEntity.ok(eventi);
    }

    @GetMapping("/descriptions")
    public ResponseEntity<Map<String, List<String>>> getAllDescrizioniGroupedByCategoria() {
        Map<String, List<String>> groupedDescriptions = eventoService.getAllDescrizioniGroupedByCategoria();
        if (groupedDescriptions == null || groupedDescriptions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groupedDescriptions);
    }


    @GetMapping
    public List<Evento> getAllEventi() {
        return eventoService.getAllEventi();
    }

    @GetMapping("/{id}")
    public Optional<Evento> getEventoById(@PathVariable Long id) {
        return eventoService.getEventoById(id);
    }

    @GetMapping("/search")
    public List<Evento> searchEventi(@RequestParam String nome) {
        return eventoService.searchEventiByNome(nome);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('GESTORE_EVENTO', 'ADMIN')")
    public Evento createEvento(@RequestBody Evento evento) {
        return eventoService.saveEvento(evento);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('GESTORE_EVENTO', 'ADMIN')")
    public void deleteEvento(@PathVariable Long id) {
        eventoService.deleteEvento(id);
    }

    @PatchMapping("/{id}/chiudi")
    @PreAuthorize("hasAnyRole('GESTORE_EVENTO', 'ADMIN')")
    public ResponseEntity<String> chiudiEvento(@PathVariable Long id) {
        eventoService.chiudiEvento(id);
        return ResponseEntity.ok("Evento chiuso, quote non giocabili.");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('GESTORE_EVENTO', 'ADMIN')")
    public ResponseEntity<Evento> updateEvento(@PathVariable Long id, @RequestBody Evento eventoDetails) {
        Evento updatedEvento = eventoService.updateEvento(id, eventoDetails);
        return ResponseEntity.ok(updatedEvento);
    }

}
