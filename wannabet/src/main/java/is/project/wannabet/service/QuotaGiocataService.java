package is.project.wannabet.service;

import is.project.wannabet.model.QuotaGiocata;
import is.project.wannabet.repository.QuotaGiocataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuotaGiocataService {

    @Autowired
    private QuotaGiocataRepository quotaGiocataRepository;

    public List<QuotaGiocata> getQuoteGiocateByScommessa(Long idScommessa) {
        return quotaGiocataRepository.findByScommessa_IdScommessa(idScommessa);
    }

    public void salvaQuotaGiocata(QuotaGiocata quotaGiocata) {
        quotaGiocataRepository.save(quotaGiocata);
    }
}
