package is.project.wannabet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import is.project.wannabet.model.Scommessa;
import is.project.wannabet.service.ScommessaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/scommesse")
public class ScommessaController {

    @Autowired
    private ScommessaService scommessaService;

    @GetMapping
    public List<Scommessa> getAllScommesse() {
        return scommessaService.getAllScommesse();
    }

    @GetMapping("/{id}")
    public Optional<Scommessa> getScommessaById(@PathVariable Long id) {
        return scommessaService.getScommessaById(id);
    }

    @PostMapping
    public Scommessa createScommessa(@RequestBody Scommessa scommessa) {
        return scommessaService.saveScommessa(scommessa);
    }

    @DeleteMapping("/{id}")
    public void deleteScommessa(@PathVariable Long id) {
        scommessaService.deleteScommessa(id);
    }
}
