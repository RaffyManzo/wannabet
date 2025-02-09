package is.project.wannabet.factory;

import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.model.Quota;
import is.project.wannabet.model.Scommessa;
import is.project.wannabet.model.StatoScommessa;

import java.util.Date;
import java.util.List;

public class ScommessaFactory {

    public static Scommessa createScommessa(AccountRegistrato account, List<Quota> quote, double importo) {
        if (account == null) {
            throw new IllegalArgumentException("La scommessa deve essere associata a un account");
        }
        if (quote == null || quote.isEmpty()) {
            throw new IllegalArgumentException("Una scommessa deve contenere almeno una quota");
        }
        if (importo < 1 ) {
            throw new IllegalArgumentException("L'importo della scommessa deve essere positivo e superiore o uguale ad 1");
        }



        Scommessa scommessa = new Scommessa();
        scommessa.setAccount(account);
        scommessa.setQuote(quote);
        scommessa.setImporto(importo);
        scommessa.setData(new Date()); // Imposta la data della scommessa
        scommessa.setStato(StatoScommessa.DA_REFERTARE); // Stato iniziale della scommessa


        double vincita = scommessa.getImporto() * scommessa.getQuote().stream()
                .mapToDouble(q -> q.getMoltipicatore())
                .reduce(1, (a, b) -> a * b);

        scommessa.setVincita(vincita);
        return scommessa;
    }
}
