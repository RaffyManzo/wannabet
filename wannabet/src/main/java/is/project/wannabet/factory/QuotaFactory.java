package is.project.wannabet.factory;

import is.project.wannabet.model.Quota;
import is.project.wannabet.model.Evento;
import is.project.wannabet.model.StatoQuota;

public class QuotaFactory {

    public static Quota createQuota(Evento evento, double moltiplicatore, String descrizione, String categoria) {
        Quota quota = new Quota();
        quota.setEvento(evento);
        quota.setMoltiplicatore(moltiplicatore);
        quota.setEsito(descrizione);
        quota.setCategoria(categoria);
        quota.setStato(StatoQuota.DA_REFERTARE);
        quota.setChiusa(false);

        return quota;
    }
}
