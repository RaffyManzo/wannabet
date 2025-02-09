package is.project.wannabet.service;

import is.project.wannabet.model.Quota;
import is.project.wannabet.model.Scontrino;
import org.springframework.stereotype.Service;

@Service
public class ScontrinoService {

    public void aggiungiQuota(Scontrino scontrino, Quota quota) {
        scontrino.aggiungiQuota(quota);
    }

    public void rimuoviQuota(Scontrino scontrino, Quota quota) {
        scontrino.rimuoviQuota(quota);
    }

    public void svuotaScontrino(Scontrino scontrino) {
        scontrino.svuotaScontrino();
    }
}
