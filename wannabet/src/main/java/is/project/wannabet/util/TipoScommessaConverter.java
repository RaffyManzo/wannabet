package is.project.wannabet.util;

import is.project.wannabet.model.TipoScommessa;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Convertitore JPA per la classe {@link TipoScommessa}.
 * Permette la conversione bidirezionale tra enum e stringhe nel database.
 */
@Converter(autoApply = true)
public class TipoScommessaConverter implements AttributeConverter<TipoScommessa, String> {

    /**
     * Converte un valore di {@link TipoScommessa} in una stringa da memorizzare nel database.
     * @param tipo Il tipo di scommessa da convertire.
     * @return La stringa corrispondente ("Prenotata" o "Giocata").
     */
    @Override
    public String convertToDatabaseColumn(TipoScommessa tipo) {
        if (tipo == null) return null;
        return switch (tipo) {
            case PRENOTATA -> "Prenotata";
            case GIOCATA -> "Giocata";
        };
    }

    /**
     * Converte una stringa dal database in un'istanza di {@link TipoScommessa}.
     * @param dbData Il valore testuale memorizzato nel database ("Prenotata" o "Giocata").
     * @return L'enum {@link TipoScommessa} corrispondente.
     */
    @Override
    public TipoScommessa convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) return null;

        System.out.println("Convertendo valore dal DB: " + dbData);

        return switch (dbData) {
            case "Prenotata" -> TipoScommessa.PRENOTATA;
            case "Giocata" -> TipoScommessa.GIOCATA;
            default -> throw new IllegalArgumentException("Tipo di scommessa sconosciuto nel database: " + dbData);
        };
    }

}
