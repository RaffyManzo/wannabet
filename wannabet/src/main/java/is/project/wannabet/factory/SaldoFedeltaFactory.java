package is.project.wannabet.factory;

import is.project.wannabet.model.SaldoFedelta;
import is.project.wannabet.service.SaldoFedeltaService;

public class SaldoFedeltaFactory {
    public static SaldoFedelta createSaldoFedelta() {
        SaldoFedelta saldoFedelta = new SaldoFedelta();

        saldoFedelta.setPunti(0);

        return saldoFedelta;
    }
}
