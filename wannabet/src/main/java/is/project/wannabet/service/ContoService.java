package is.project.wannabet.service;

import is.project.wannabet.model.Conto;
import is.project.wannabet.repository.ContoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContoService {

    @Autowired
    private ContoRepository contoRepository;

    public List<Conto> getAllConti() {
        return contoRepository.findAll();
    }

    public Optional<Conto> getContoById(Long id) {
        return contoRepository.findById(id);
    }

    public Conto saveConto(Conto conto) {
        return contoRepository.save(conto);
    }

    public void deleteConto(Long id) {
        contoRepository.deleteById(id);
    }
}
