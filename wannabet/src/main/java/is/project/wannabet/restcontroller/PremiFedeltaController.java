package is.project.wannabet.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import is.project.wannabet.model.PremiFedelta;
import is.project.wannabet.service.PremiFedeltaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/premi")
public class PremiFedeltaController {

    @Autowired
    private PremiFedeltaService premiFedeltaService;

    @GetMapping
    public List<PremiFedelta> getAllPremi() {
        return premiFedeltaService.getAllPremi();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('UTENTE')")
    public Optional<PremiFedelta> getPremioById(@PathVariable Long id) {
        return premiFedeltaService.getPremioById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('GESTORE_FEDELTA', 'ADMIN')")
    public PremiFedelta createPremio(@RequestBody PremiFedelta premio) {
        return premiFedeltaService.savePremio(premio);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('GESTORE_FEDELTA', 'ADMIN')")
    public void deletePremio(@PathVariable Long id) {
        premiFedeltaService.deletePremio(id);
    }
}
