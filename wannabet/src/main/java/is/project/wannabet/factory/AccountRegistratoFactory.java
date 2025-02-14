package is.project.wannabet.factory;

import is.project.wannabet.model.AccountRegistrato;
import is.project.wannabet.model.Conto;
import is.project.wannabet.model.SaldoFedelta;
import is.project.wannabet.model.TipoAccount;

import java.util.Date;

public class AccountRegistratoFactory {
    public static AccountRegistrato createAccountRegistrato(String nome, String cognome,
                                                            String codiceFiscale, Conto conto,
                                                            Date dataDiNascita, String email,
                                                            String password, SaldoFedelta saldoFedelta) {
        AccountRegistrato accountRegistrato = new AccountRegistrato();

        accountRegistrato.setEmail(email);
        accountRegistrato.setNome(nome);
        accountRegistrato.setCognome(cognome);
        accountRegistrato.setCodiceFiscale(codiceFiscale);
        accountRegistrato.setDataNascita(dataDiNascita);
        accountRegistrato.setConto(conto);
        accountRegistrato.setSaldoFedelta(saldoFedelta);
        accountRegistrato.setPassword(password);
        accountRegistrato.setTipo(TipoAccount.UTENTE);

        return accountRegistrato;
    }
}
