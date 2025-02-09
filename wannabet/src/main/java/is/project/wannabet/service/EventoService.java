package is.project.wannabet.service;

import is.project.wannabet.factory.EventoFactory;
import is.project.wannabet.model.Quota;
import is.project.wannabet.repository.QuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import is.project.wannabet.model.Evento;
import is.project.wannabet.repository.EventoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private QuotaRepository quotaRepository;

    public List<Evento> getAllEventi() {
        return eventoRepository.findAll();
    }

    public Optional<Evento> getEventoById(Long id) {
        return eventoRepository.findById(id);
    }

    public List<Evento> searchEventiByNome(String nome) {
        return eventoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Evento saveEvento(Evento evento) {
        return eventoRepository.save(evento);
    }

    public void deleteEvento(Long id) {
        eventoRepository.deleteById(id);
    }

    public void chiudiEvento(Long idEvento) {
        Optional<Evento> eventoOpt = eventoRepository.findById(idEvento);
        if (eventoOpt.isPresent()) {
            Evento evento = eventoOpt.get();
            evento.setChiuso(true);
            eventoRepository.save(evento);

            // Aggiorna tutte le quote dell'evento come chiuse
            List<Quota> quoteEvento = quotaRepository.findByEventoId(idEvento);
            for (Quota quota : quoteEvento) {
                quota.setChiusa(true);
                quotaRepository.save(quota);
            }
        }
    }

    public Evento createEvento(String nome, Date data, String descrizione, String categoria) {
        Evento evento = EventoFactory.createEvento(nome, data, descrizione, categoria);
        return eventoRepository.save(evento);
    }
}
