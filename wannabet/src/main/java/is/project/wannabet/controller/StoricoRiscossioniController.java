package is.project.wannabet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import is.project.wannabet.model.StoricoRiscossioni;
import is.project.wannabet.service.StoricoRiscossioniService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/storicoRiscossioni")
public class StoricoRiscossioniController {

    @Autowired
    private StoricoRiscossioniService storicoRiscossioniService;

    @GetMapping
    public List<StoricoRiscossioni> getAllRiscossioni() {
        return storicoRiscossioniService.getAllRiscossioni();
    }

    @GetMapping("/{id}")
    public Optional<StoricoRiscossioni> getRiscossioneById(@PathVariable Long id) {
        return storicoRiscossioniService.getRiscossioneById(id);
    }

    @GetMapping("/saldo/{saldoFedeltaId}")
    public List<StoricoRiscossioni> getRiscossioniBySaldo(@PathVariable Long saldoFedeltaId) {
        return storicoRiscossioniService.getRiscossioniBySaldoFedelta(saldoFedeltaId);
    }

    @PostMapping
    public StoricoRiscossioni createRiscossione(@RequestBody StoricoRiscossioni riscossione) {
        return storicoRiscossioniService.saveRiscossione(riscossione);
    }

    @DeleteMapping("/{id}")
    public void deleteRiscossione(@PathVariable Long id) {
        storicoRiscossioniService.deleteRiscossione(id);
    }
}
