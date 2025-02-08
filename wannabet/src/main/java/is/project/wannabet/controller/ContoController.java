package is.project.wannabet.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import is.project.wannabet.model.Conto;
import is.project.wannabet.service.ContoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/conto")
public class ContoController {

    @Autowired
    private ContoService contoService;

    @GetMapping
    public List<Conto> getAllConti() {
        return contoService.getAllConti();
    }

    @GetMapping("/{id}")
    public Optional<Conto> getContoById(@PathVariable Long id) {
        return contoService.getContoById(id);
    }

    @PostMapping
    public Conto createConto(@RequestBody Conto conto) {
        return contoService.saveConto(conto);
    }

    @DeleteMapping("/{id}")
    public void deleteConto(@PathVariable Long id) {
        contoService.deleteConto(id);
    }
}

