package is.project.wannabet.factory;

import is.project.wannabet.model.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Factory per la creazione di oggetti {@link Scommessa}.
 * Fornisce metodi statici per creare istanze di scommesse con parametri predefiniti.
 */
public class ScommessaFactory {

    /**
     * Crea una nuova scommessa con l'importo e la lista di quote giocate.
     *
     * @param account L'account registrato che effettua la scommessa.
     * @param quote La lista delle quote giocate nella scommessa.
     * @param importo L'importo scommesso.
     * @return Una nuova istanza di {@link Scommessa}.
     */
    public static Scommessa createScommessa(AccountRegistrato account, List<Quota> quote, double importo) {
        if (quote == null || quote.isEmpty()) {
            throw new IllegalArgumentException("Una scommessa deve contenere almeno una quota.");
        }
        if (importo <= 0) {
            throw new IllegalArgumentException("L'importo della scommessa deve essere positivo.");
        }

        Scommessa scommessa = new Scommessa();
        scommessa.setAccount(account);
        scommessa.setData(new Date());
        scommessa.setImporto(importo);
        scommessa.setStato(StatoScommessa.DA_REFERTARE);

        // Creiamo la lista delle QuoteGiocate
        List<QuotaGiocata> quoteGiocate = quote.stream()
                .map(q -> new QuotaGiocata(scommessa, q)) // Creiamo le quote giocate congelando il moltiplicatore
                .collect(Collectors.toList());

        scommessa.setVincita(importo * quoteGiocate.stream()
                .map(QuotaGiocata::getQuota)
                .mapToDouble(Quota::getMoltiplicatore)
                .reduce(1.0, (a, b) -> a * b)
        );

        scommessa.setQuoteGiocate(quoteGiocate);
        return scommessa;
    }
}
