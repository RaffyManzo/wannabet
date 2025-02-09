package is.project.wannabet.factory;

import is.project.wannabet.model.Evento;
import is.project.wannabet.model.Quota;
import is.project.wannabet.model.StatoQuota;

public class QuotaFactory {

    public static Quota createQuota(String descrizione, String categoria, double moltiplicatore, Evento evento) {
        if (descrizione == null || descrizione.isEmpty()) {
            throw new IllegalArgumentException("La descrizione della quota non può essere vuota");
        }
        if (categoria == null || categoria.isEmpty()) {
            throw new IllegalArgumentException("La categoria della quota non può essere vuota");
        }
        if (moltiplicatore <= 0) {
            throw new IllegalArgumentException("Il moltiplicatore della quota deve essere positivo");
        }
        if (evento == null) {
            throw new IllegalArgumentException("La quota deve essere associata a un evento");
        }

        Quota quota = new Quota();
        quota.setDescrizione(descrizione);
        quota.setCategoria(categoria);
        quota.setReferto(null);
        quota.setMoltipicatore(moltiplicatore);
        quota.setEvento(evento);
        quota.setStato(StatoQuota.DA_REFERTARE); // Stato iniziale della quota
        quota.setChiusa(false); // Le quote iniziano sempre come aperte

        return quota;
    }
}
