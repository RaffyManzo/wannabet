package is.project.wannabet.util;

import is.project.wannabet.model.StatoScommessa;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatoScommessaConverter implements AttributeConverter<StatoScommessa, String> {

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
