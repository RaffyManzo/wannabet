package is.project.wannabet.factory;

import is.project.wannabet.model.*;
import java.util.List;
import java.util.stream.Collectors;

public class ScommessaFactory {

    public static Scommessa createScommessa(AccountRegistrato account, List<Quota> quote, double importo) {
        if (quote == null || quote.isEmpty()) {
            throw new IllegalArgumentException("Una scommessa deve contenere almeno una quota.");
        }
        if (importo <= 0) {
            throw new IllegalArgumentException("L'importo della scommessa deve essere positivo.");
        }

        Scommessa scommessa = new Scommessa();
        scommessa.setAccount(account);
        scommessa.setImporto(importo);
        scommessa.setStato(StatoScommessa.DA_REFERTARE);

        // Creiamo la lista delle QuoteGiocate
        List<QuotaGiocata> quoteGiocate = quote.stream()
                .map(q -> new QuotaGiocata(scommessa, q)) // Creiamo le quote giocate congelando il moltiplicatore
                .collect(Collectors.toList());

        scommessa.setQuoteGiocate(quoteGiocate);
        return scommessa;
    }
}
