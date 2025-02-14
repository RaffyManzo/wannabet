package is.project.wannabet.factory;

import is.project.wannabet.model.Conto;

import java.util.Date;

public class ContoFactory {

    public static Conto createConto(String indirizzoDiFatturazione) {
        Conto conto = new Conto();
        conto.setSaldo(0.0);
        conto.setDataCreazione(new Date());
        conto.setIndirizzoFatturazione(indirizzoDiFatturazione);

        return conto;
    }
}
