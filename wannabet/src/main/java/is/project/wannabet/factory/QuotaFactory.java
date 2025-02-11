package is.project.wannabet.factory;

import is.project.wannabet.model.Evento;
import is.project.wannabet.model.Quota;
import is.project.wannabet.model.StatoQuota;

/**
 * Factory per la creazione di oggetti {@link Quota}.
 * Fornisce metodi per creare istanze di quote con parametri specifici.
 */
public class QuotaFactory {

    /**
     * Crea una nuova quota associata a un evento.
     *
     * @param evento L'evento a cui è associata la quota.
     * @param moltiplicatore Il valore del moltiplicatore della quota.
     * @param esito L'esito rappresentato dalla quota (es. "1", "X", "2").
     * @param categoria La categoria della quota (es. "Risultato Finale").
     * @return Un'istanza di {@link Quota} con i parametri forniti.
     */
    public static Quota createQuota(Evento evento, double moltiplicatore, String esito, String categoria) {
        Quota quota = new Quota();
        quota.setEvento(evento);
        quota.setMoltiplicatore(moltiplicatore);
        quota.setEsito(esito);
        quota.setCategoria(categoria);
        quota.setStato(is.project.wannabet.model.StatoQuota.DA_REFERTARE);
        quota.setChiusa(false);
        return quota;
    }

    /**
     * Crea una nuova quota con specifica chiusura.
     *
     * @param evento L'evento a cui è associata la quota.
     * @param moltiplicatore Il valore del moltiplicatore della quota.
     * @param esito L'esito rappresentato dalla quota (es. "1", "X", "2").
     * @param categoria La categoria della quota (es. "Risultato Finale").
     * @param chiusa Indica se la quota è chiusa o meno.
     * @return Un'istanza di {@link Quota} con i parametri forniti.
     */
    public static Quota createQuota(Evento evento, double moltiplicatore, String esito, String categoria, boolean chiusa) {
        Quota quota = new Quota();
        quota.setEvento(evento);
        quota.setMoltiplicatore(moltiplicatore);
        quota.setEsito(esito);
        quota.setCategoria(categoria);
        quota.setStato(StatoQuota.DA_REFERTARE);
        quota.setChiusa(chiusa);
        return quota;
    }
}
