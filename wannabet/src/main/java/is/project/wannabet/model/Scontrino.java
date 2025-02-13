package is.project.wannabet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Scontrino {

    @JsonIgnoreProperties("evento")
    @JsonProperty("quote")
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
        if (!quote.contains(quota)) { // Evita duplicati
            quote.add(quota);
        }
    }

    public boolean rimuoviQuota(Quota quota) {
        Iterator<Quota> iterator = quote.iterator();
        while (iterator.hasNext()) {
            Quota q = iterator.next();
            if (q.getIdQuota().equals(quota.getIdQuota())) {
                iterator.remove();
                return true; // Conferma la rimozione
            }
        }
        return false; // Quota non trovata
    }

    public void svuotaScontrino() {
        quote.clear();
    }
}
