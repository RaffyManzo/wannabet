package is.project.wannabet.util;

import is.project.wannabet.model.StatoScommessa;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Convertitore JPA per la classe {@link StatoScommessa}.
 * Consente la conversione tra enum e stringhe per la persistenza nel database.
 */
@Converter(autoApply = true)
public class StatoScommessaConverter implements AttributeConverter<StatoScommessa, String> {

    /**
     * Converte lo stato della scommessa in una stringa per il database.
     * @param stato Lo stato della scommessa da convertire.
     * @return La stringa corrispondente da memorizzare nel database.
     */
    @Override
    public String convertToDatabaseColumn(StatoScommessa stato) {
        if (stato == null) {
            return null;
        }
        switch (stato) {
            case DA_REFERTARE:
                return "Da refertare";
            case VINTA:
                return "Vinta";
            case PERSA:
                return "Persa";
            default:
                throw new IllegalArgumentException("Stato sconosciuto: " + stato);
        }
    }

    /**
     * Converte una stringa dal database in un'istanza di {@link StatoScommessa}.
     * @param dbData Il valore testuale memorizzato nel database.
     * @return L'enum {@link StatoScommessa} corrispondente.
     */
    @Override
    public StatoScommessa convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        switch (dbData) {
            case "Da refertare":
                return StatoScommessa.DA_REFERTARE;
            case "Vinta":
                return StatoScommessa.VINTA;
            case "Persa":
                return StatoScommessa.PERSA;
            default:
                throw new IllegalArgumentException("Valore sconosciuto nel DB: " + dbData);
        }
    }
}
