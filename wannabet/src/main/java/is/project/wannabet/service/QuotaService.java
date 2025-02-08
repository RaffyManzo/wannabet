package is.project.wannabet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import is.project.wannabet.model.Quota;
import is.project.wannabet.repository.QuotaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class QuotaService {

    @Autowired
    private QuotaRepository quotaRepository;

    public List<Quota> getAllQuote() {
        return quotaRepository.findAll();
    }

    public Optional<Quota> getQuotaById(Long id) {
        return quotaRepository.findById(id);
    }

    public List<Quota> getQuoteByEvento(Long eventoId) {
        return quotaRepository.findByEventoId(eventoId);
    }

    public Quota saveQuota(Quota quota) {
        return quotaRepository.save(quota);
    }

    public void deleteQuota(Long id) {
        quotaRepository.deleteById(id);

    }
}