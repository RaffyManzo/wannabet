package is.project.wannabet.factory;

import is.project.wannabet.model.Evento;

import java.util.Date;

public class EventoFactory {

    public static Evento createEvento(String nome, Date data, String descrizione, String categoria) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Il nome dell'evento non può essere vuoto");
        }
        if (data == null || data.before(new Date())) {
            throw new IllegalArgumentException("La data dell'evento deve essere nel futuro");
        }
        if (descrizione == null || descrizione.isEmpty()) {
            throw new IllegalArgumentException("La descrizione dell'evento non può essere vuota");
        }
        if (categoria == null || categoria.isEmpty()) {
            throw new IllegalArgumentException("La categoria dell'evento non può essere vuota");
        }

        Evento evento = new Evento();
        evento.setNome(nome);
        evento.setData(data);
        evento.setDescrizione(descrizione);
        evento.setCategoria(categoria);
        evento.setChiuso(false); // Gli eventi iniziano sempre come aperti

        return evento;
    }
}
