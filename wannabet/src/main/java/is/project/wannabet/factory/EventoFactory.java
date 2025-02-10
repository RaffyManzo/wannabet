package is.project.wannabet.factory;

import is.project.wannabet.model.Evento;
import java.util.Date;

/**
 * Factory per la creazione di oggetti {@link Evento}.
 * Permette di creare istanze di eventi con parametri predefiniti.
 */
public class EventoFactory {

    /**
     * Crea un nuovo evento con i parametri specificati.
     *
     * @param nome Il nome dell'evento.
     * @param data La data dell'evento.
     * @param descrizione La descrizione dell'evento.
     * @param categoria La categoria dell'evento (es. "Calcio", "Tennis").
     * @return Un'istanza di {@link Evento} con i parametri forniti.
     */
    public static Evento createEvento(String nome, Date data, String descrizione, String categoria) {
        Evento evento = new Evento();
        evento.setNome(nome);
        evento.setData(data);
        evento.setDescrizione(descrizione);
        evento.setCategoria(categoria);
        evento.setChiuso(false); // Gli eventi sono inizialmente aperti
        return evento;
    }
}
