package is.project.wannabet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import is.project.wannabet.model.Evento;
import is.project.wannabet.service.EventoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/evento")
public class EventoController {

    @Autowired
    private EventoService eventoService;

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

    @PostMapping
    public Evento createEvento(@RequestBody Evento evento) {
        return eventoService.saveEvento(evento);
    }

    @DeleteMapping("/{id}")
    public void deleteEvento(@PathVariable Long id) {
        eventoService.deleteEvento(id);
    }

    @PatchMapping("/{id}/chiudi")
    public ResponseEntity<String> chiudiEvento(@PathVariable Long id) {
        eventoService.chiudiEvento(id);
        return ResponseEntity.ok("Evento chiuso, quote non giocabili.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> updateEvento(@PathVariable Long id, @RequestBody Evento eventoDetails) {
        Evento updatedEvento = eventoService.updateEvento(id, eventoDetails);
        return ResponseEntity.ok(updatedEvento);
    }

}
