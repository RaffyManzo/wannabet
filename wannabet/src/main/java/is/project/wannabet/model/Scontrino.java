package is.project.wannabet.model;

import java.util.ArrayList;
import java.util.List;

public class Scontrino {

    private List<Quota> quote;

    public Scontrino() {
        this.quote = new ArrayList<>();
    }

    public List<Quota> getQuote() {
        return quote;
    }

    public void setQuote(List<Quota> quote) {
        this.quote = quote;
    }

    public void aggiungiQuota(Quota quota) {
        this.quote.add(quota);
    }

    public void rimuoviQuota(Quota quota) {
        this.quote.remove(quota);
    }

    public void svuotaScontrino() {
        this.quote.clear();
    }
}
