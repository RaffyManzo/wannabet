package is.project.wannabet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import is.project.wannabet.model.SaldoFedelta;
import is.project.wannabet.service.SaldoFedeltaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/saldoFedelta")
public class SaldoFedeltaController {

    @Autowired
    private SaldoFedeltaService saldoFedeltaService;

    @GetMapping
    public List<SaldoFedelta> getAllSaldiFedelta() {
        return saldoFedeltaService.getAllSaldiFedelta();
    }

    @GetMapping("/{id}")
    public Optional<SaldoFedelta> getSaldoFedeltaById(@PathVariable Long id) {
        return saldoFedeltaService.getSaldoFedeltaById(id);
    }

    @PostMapping
    public SaldoFedelta createSaldoFedelta(@RequestBody SaldoFedelta saldoFedelta) {
        return saldoFedeltaService.saveSaldoFedelta(saldoFedelta);
    }

    @DeleteMapping("/{id}")
    public void deleteSaldoFedelta(@PathVariable Long id) {
        saldoFedeltaService.deleteSaldoFedelta(id);
    }
}
