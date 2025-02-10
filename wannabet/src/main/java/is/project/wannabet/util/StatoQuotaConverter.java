package is.project.wannabet.util;

import is.project.wannabet.model.StatoQuota;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Convertitore JPA per la classe {@link StatoQuota}.
 * Consente la conversione bidirezionale tra enum e valori stringa per la persistenza nel database.
 */
@Converter(autoApply = true)
public class StatoQuotaConverter implements AttributeConverter<StatoQuota, String> {

    /**
     * Converte lo stato dell'enum in una stringa per l'archiviazione nel database.
     * @param stato Lo stato della quota da convertire.
     * @return La stringa corrispondente nel database.
     */
    @Override
    public String convertToDatabaseColumn(StatoQuota stato) {
        if (stato == null) {
            return null;
        }
        switch (stato) {
            case DA_REFERTARE:
                return "Da Refertare";
            case VINCENTE:
                return "Vincente";
            case PERDENTE:
                return "Perdente";
            default:
                throw new IllegalArgumentException("Stato sconosciuto: " + stato);
        }
    }

    /**
     * Converte una stringa del database in un'istanza di {@link StatoQuota}.
     * @param dbData Il valore memorizzato nel database.
     * @return L'enum {@link StatoQuota} corrispondente.
     */
    @Override
    public StatoQuota convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        switch (dbData) {
            case "Da Refertare":
                return StatoQuota.DA_REFERTARE;
            case "Vincente":
                return StatoQuota.VINCENTE;
            case "Perdente":
                return StatoQuota.PERDENTE;
            default:
                throw new IllegalArgumentException("Valore sconosciuto nel DB: " + dbData);
        }
    }
}
